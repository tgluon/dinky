create database dim;
use dim;
DROP TABLE IF EXISTS dim_comment_keyword;
CREATE TABLE if not  EXISTS dim_comment_keyword  (
                                                      id int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台',
    keyword varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关键词',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX `keyword_un_idx1`(`platform`,`keyword`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论关键词' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS dim_area;
CREATE TABLE if not  EXISTS dim_area  (
                                                     id int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台',
    area varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地域',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX `keyword_un_idx1`(`platform`,`area`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '地域' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS dim_industry;
CREATE TABLE if not  EXISTS dim_industry  (
                                          id int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台',
    industry varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行业',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX `keyword_un_idx1`(`platform`,`industry`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '地域' ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS dim_aweme;
CREATE TABLE if not  EXISTS dim_aweme  (
                                              id int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    platform varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台',
    industry varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行业',
    area varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地域',
    aweme_id        varchar(200) not null comment '视频id/笔记id',
    aweme_url       varchar(100) DEFAULT NULL comment '视频链接',
    title           longtext     null comment '视频标题',
    `desc`          longtext     null comment '视频描述',
    aweme_create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX `keyword_un_idx1`(`platform`,`aweme_id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频信息' ROW_FORMAT = Dynamic;
