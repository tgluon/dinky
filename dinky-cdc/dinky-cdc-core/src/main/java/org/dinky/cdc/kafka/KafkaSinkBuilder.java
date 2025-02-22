/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.dinky.cdc.kafka;

/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import org.dinky.assertion.Asserts;
import org.dinky.cdc.AbstractSinkBuilder;
import org.dinky.cdc.SinkBuilder;
import org.dinky.data.model.FlinkCDCConfig;
import org.dinky.data.model.Schema;
import org.dinky.data.model.Table;
import org.dinky.executor.CustomTableEnvironment;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * MysqlCDCBuilder
 **/
public class KafkaSinkBuilder extends AbstractSinkBuilder implements Serializable {

    public static final String KEY_WORD = "datastream-kafka";
    private Properties kafkaProducerConfig;

    public KafkaSinkBuilder() {}

    public KafkaSinkBuilder(FlinkCDCConfig config) {
        super(config);
    }

    @Override
    public void addSink(
            DataStream<RowData> rowDataDataStream,
            Table table,
            List<String> columnNameList,
            List<LogicalType> columnTypeList) {}

    @Override
    public String getHandle() {
        return KEY_WORD;
    }

    @Override
    public SinkBuilder create(FlinkCDCConfig config) {
        return new KafkaSinkBuilder(config);
    }

    @Override
    public void build(
            StreamExecutionEnvironment env,
            CustomTableEnvironment customTableEnvironment,
            DataStreamSource<String> dataStreamSource) {
        init(env, customTableEnvironment);

        kafkaProducerConfig = getProperties();
        if (Asserts.isNotNullString(config.getSink().get("topic"))) {
            singleTopicSink(dataStreamSource);
        } else {
            multipleTopicSink(dataStreamSource);
        }
    }

    private void singleTopicSink(DataStreamSource<String> dataStreamSource) {
        org.apache.flink.connector.kafka.sink.KafkaSinkBuilder<String> kafkaSinkBuilder = KafkaSink.<String>builder()
                .setBootstrapServers(config.getSink().get("brokers"))
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(config.getSink().get("topic"))
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build())
                .setTransactionalIdPrefix(
                        config.getSink().get("transactional.id.prefix") == null
                                ? ""
                                : config.getSink().get("transactional.id.prefix"))
                .setDeliverGuarantee(DeliveryGuarantee.valueOf(
                        config.getSink().get("delivery.guarantee") == null
                                ? "NONE"
                                : config.getSink().get("delivery.guarantee")));
        if (!kafkaProducerConfig.isEmpty()) {
            kafkaSinkBuilder.setKafkaProducerConfig(kafkaProducerConfig);
        }
        KafkaSink<String> kafkaSink = kafkaSinkBuilder.build();
        dataStreamSource.sinkTo(kafkaSink);
    }

    private void multipleTopicSink(DataStreamSource<String> dataStreamSource) {
        final Map<Table, OutputTag<String>> tagMap = new LinkedHashMap<>();
        final Map<String, Table> tableMap = new LinkedHashMap<>();
        final Map<String, String> tableTopicMap = this.getTableTopicMap();
        final ObjectMapper objectMapper = new ObjectMapper();

        SingleOutputStreamOperator<Map> mapOperator = deserialize(dataStreamSource);
        logger.info("Build deserialize successful...");

        for (Schema schema : getSortedSchemaList()) {
            for (Table table : schema.getTables()) {
                String sinkTableName = getSinkTableName(table);
                OutputTag<String> outputTag = new OutputTag<String>(sinkTableName) {};
                tagMap.put(table, outputTag);
                tableMap.put(table.getSchemaTableName(), table);
            }
        }
        partitionByTableAndPrimarykey(mapOperator, tableMap);
        logger.info("Build partitionBy successful...");
        final String schemaFieldName = config.getSchemaFieldName();
        SingleOutputStreamOperator<String> process = mapOperator.process(new ProcessFunction<Map, String>() {
            @Override
            public void processElement(Map map, ProcessFunction<Map, String>.Context ctx, Collector<String> out)
                    throws Exception {
                LinkedHashMap source = (LinkedHashMap) map.get("source");
                try {
                    String result = objectMapper.writeValueAsString(map);
                    Table table = tableMap.get(source.get(schemaFieldName).toString() + "."
                            + source.get("table").toString());
                    OutputTag<String> outputTag = tagMap.get(table);
                    ctx.output(outputTag, result);
                } catch (Exception e) {
                    out.collect(objectMapper.writeValueAsString(map));
                }
            }
        });
        logger.info("Build shunt successful...");
        tagMap.forEach((k, v) -> {
            String topic = getSinkTableName(k);
            if (tableTopicMap != null) {
                String tableName = k.getName();
                String newTopic = tableTopicMap.get(tableName);
                if (Asserts.isNotNullString(newTopic)) {
                    topic = newTopic;
                }
            }
            org.apache.flink.connector.kafka.sink.KafkaSinkBuilder<String> kafkaSinkBuilder =
                    KafkaSink.<String>builder()
                            .setBootstrapServers(config.getSink().get("brokers"))
                            .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                                    .setTopic(topic)
                                    .setValueSerializationSchema(new SimpleStringSchema())
                                    .build())
                            .setTransactionalIdPrefix(
                                    config.getSink().get("transactional.id.prefix") == null
                                            ? ""
                                            : config.getSink().get("transactional.id.prefix"))
                            .setDeliverGuarantee(DeliveryGuarantee.valueOf(
                                    config.getSink().get("delivery.guarantee") == null
                                            ? "NONE"
                                            : config.getSink().get("delivery.guarantee")));
            if (!kafkaProducerConfig.isEmpty()) {
                kafkaSinkBuilder.setKafkaProducerConfig(kafkaProducerConfig);
            }

            KafkaSink<String> kafkaSink = kafkaSinkBuilder.build();
            process.getSideOutput(v).rebalance().sinkTo(kafkaSink).name(topic);
        });
    }
}
