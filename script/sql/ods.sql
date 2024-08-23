DROP TABLE IF EXISTS ods_aweme_comment;

CREATE TABLE IF NOT EXISTS ods_aweme_comment (
    id int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    platform varchar(10) NOT NULL COMMENT '平台ID',
    aweme_id varchar(64) NOT NULL COMMENT '视频ID',
    user_id varchar(256) DEFAULT NULL COMMENT '用户id',
    comment_id varchar(256) DEFAULT NULL COMMENT '评论id',
    nickname varchar(64) DEFAULT NULL COMMENT '昵称',
    ip_location varchar(10) DEFAULT NULL COMMENT '用户ip地址',
    content longtext DEFAULT NULL COMMENT '评论内容',
    user_url varchar(256) DEFAULT NULL COMMENT '用户url',
    create_time datetime COMMENT '评论时间',
    keyword  varchar(64) DEFAULT NULL COMMENT '关键词',
    PRIMARY KEY (id),
    UNIQUE `dac_un_idx1` USING BTREE (`platform`,`aweme_id`, `comment_id`)
    ) ENGINE = InnoDB CHARSET = utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '评论';


