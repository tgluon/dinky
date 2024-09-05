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

package org.dinky.udf;

import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeRuleEngine extends ScalarFunction {
    private static final Map<String, List<String>> map = new HashMap<>();

    /**
     * 初始化加载评论规则维表
     *
     * @param context
     * @throws Exception
     */
    @Override
    public void open(FunctionContext context) throws Exception {
        final String URL =
                "jdbc:mysql://mysql.middle-server:3306/dim?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
        final String USERNAME = "root";
        final String PASSWORD = "bigdata123";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "select platform,keyword from dim_comment_keyword";
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            // 遍历加入
            while (rs.next()) {
                String platform = rs.getString(1);
                String keyword = rs.getString(2);
                if (map.containsKey(platform)) {
                    map.get(platform).add(keyword);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(keyword);
                    map.put(platform, list);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用规则匹配评论
     *
     * @param platform 平台
     * @param context  评论
     * @return
     */
    public String eval(String platform, String context) {
        List<String> keywords = map.get(platform);
        String result = "0@#@north";
        for (String word : keywords) {
            boolean contained = context.contains(word);
            if (contained) {
                result = "1" + "@#@" + word;
                return result;
            }
        }
        return result;
    }
}
