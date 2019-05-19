SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_detail`;
CREATE TABLE `user_detail`
(
  `openid`          varchar(50) NOT NULL,
  `mobile`          varchar(20),
  `mobile_verified` bit         not null default 0,
  `email`           varchar(50),
  `email_verified`  bit         not null default 0,
  `create_time`     datetime    not null default current_timestamp,
  `update_time`     datetime ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`openid`),
  unique `idx_nickname` (`mobile`),
  unique `idx_real_name` (`email`),
  index `idx_create_time` (`create_time`),
  index `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
