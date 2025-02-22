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

export default {
  'welcom.welcom': '欢迎来到Dinky！',
  'welcom.welcom.content':
    '为 Apache Flink 深度定制的新一代实时计算平台，提供敏捷的 Flink SQL, Flink Jar 作业开发、\n          部署及监控能力，助力实时计算高效应用。',
  'welcom.welcom.content.tip1': '这看起来好像是你第一次登入Dinky',
  'welcom.welcom.content.tip2': '别担心，我们只需要几步简单的向导即可畅享Dinky之旅！',
  'welcom.welcom.setPwd.tip': '设置admin密码：',
  'welcom.welcom.setPwd': '设置密码',
  'welcom.welcom.skip': '跳过此步骤',

  'welcom.next': '下一步',
  'welcom.prev': '上一步',
  'welcom.submit': '提交',
  'welcom.finish.title': '初始化完成！',
  'welcom.finish': '立即开始你的Dinky之旅吧！',

  'welcom.goLogin': '去登陆！',
  'welcom.base.config.title': '基本配置',
  'welcom.base.config.dinky.url.title': 'Dinky地址：',
  'welcom.base.config.dinky.url':
    'dinky对外服务地址，请确保k8s或yarn集群内可以正常访问此地址,否则对于Application任务可能会无法正常监控状态',
  'welcom.tips': '如果您还不清楚参数如何填写，不要担心，保持默认，后续可以随时前往配置中心进行修改',
  'welcom.base.config.taskowner.title': '作业责任人锁机制：',
  'welcom.base.config.taskowner':
    '当选择[OWNER]时，只有作业责任人才能操作作业，其他用户无法操作/修改作业，\n            当选择[OWNER_AND_MAINTAINER]时，\n            作业责任人和维护人都可以操作/修改作业， 当选择[ALL]时，所有人都可以操作/修改作业， 默认为[ALL]',

  'welcom.flink.config.title': 'Flink配置',
  'welcom.flink.config.jobwait.title': 'Job 提交等待时间：',
  'welcom.flink.config.jobwait':
    '提交 Application 或 PerJob 任务时获取 Job ID\n            的最大等待时间（秒），如果作业提交较慢，需要增大此数值',
  'welcom.flink.config.useHistoryServer.title': '使用内置 Flink History Server：',
  'welcom.flink.config.useHistoryServer':
    '此功能会在 Dinky 里面内置一个Flink History Server ，作用于 Flink 任务的历史查询，\n            使 Flink 任务减少 UNKNOWN 状态的情况，并打入 Flink 任务最后的状态信息',
  'welcom.flink.config.historyPort.title': 'Flink History Server 端口：',
  'welcom.flink.config.historyPort': '内置Flink History Server 端口，例如：8082，确保端口没有被占用'
};
