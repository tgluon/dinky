DROP TABLE IF EXISTS dwd_aweme_comment;
CREATE TABLE IF NOT EXISTS ods_aweme_comment (
    id int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    platform varchar(10) NOT NULL COMMENT '平台ID',
    aweme_id varchar(64) NOT NULL COMMENT '视频ID',
    comment_id varchar(256) DEFAULT NULL COMMENT '评论id',
    user_id varchar(256) DEFAULT NULL COMMENT '用户id',
    user_url varchar(256) DEFAULT NULL COMMENT '用户url',
    aweme_url  varchar(100) DEFAULT NULL comment '视频链接',
    nickname varchar(64) DEFAULT NULL COMMENT '昵称',
    ip_location varchar(10) DEFAULT NULL COMMENT '用户ip地址',
    content longtext DEFAULT NULL COMMENT '评论内容',
    create_time datetime COMMENT '评论时间',
    keyword  varchar(64) DEFAULT NULL COMMENT '关键词',
    PRIMARY KEY (id),
    UNIQUE `dac_un_idx1` USING BTREE (`platform`,`aweme_id`, `comment_id`)
    ) ENGINE = InnoDB CHARSET = utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '评论';


DROP TABLE IF EXISTS dwd_crawler_aweme_task;
create table IF NOT EXISTS  dwd_crawler_aweme_task
(
    id int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    platform        varchar(10)  not null comment '平台',
    aweme_id        varchar(200) not null comment '视频id/笔记id',
    aweme_url       varchar(100) DEFAULT NULL comment '视频链接',
    comment_count   int  DEFAULT NULL comment'评论数',
    collected_count int  DEFAULT NULL comment'收藏数',
    share_count     int  DEFAULT NULL comment'分享数',
    create_time     datetime default CURRENT_TIMESTAMP not null comment 'create time',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    PRIMARY KEY (id),
    UNIQUE `dact_un_idx1` USING BTREE (`platform`,`aweme_id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论爬虫任务信息表' ROW_FORMAT = Dynamic;




    aweme_id,
    comment_id,
    user_id,
    user_url,
    aweme_url,
    nickname,
    ip_location,
    content,
    create_time ,
    keyword,