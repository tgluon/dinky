FROM flink:1.18-scala_2.12-java11

ADD connectors.tar.gz /opt
ADD plugin.tar.gz /opt
ADD app.tar.gz /opt

RUN  mkdir -p /opt/flink/plugins/s3-fs-presto \
     && mv /opt/connectors/*  /opt/flink/lib/  \
     &&  mv /opt/plugin/*  /opt/flink/plugins/s3-fs-presto/ \
     && rm -rf /opt/flink/lib/flink-table-planner-loader*.jar \
     && cp  /opt/flink/opt/flink-table-planner*.jar  /opt/flink/lib/

RUN chmod -R 777 /opt/flink && chmod -R 777 /opt/app

WORKDIR /opt/flink
