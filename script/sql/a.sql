DROP TABLE IF EXISTS ods_douyin_aweme;
CREATE TABLE ods_douyin_aweme (
                              id int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                              platform varchar(10) NOT NULL COMMENT '平台ID',
                              aweme_id varchar(64) NOT NULL COMMENT '视频ID',
                              title longtext DEFAULT NULL COMMENT '视频标题',
                              PRIMARY KEY (id),
                              UNIQUE INDEX `da_un_idx1`(`aweme_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抖音视频';
