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