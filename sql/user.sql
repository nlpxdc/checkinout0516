SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
  `openid`      varchar(50) NOT NULL,
  `nickname`    varchar(50),
  `real_name`   varchar(20),
  `gender`      tinyint,
  `avatar_url`  varchar(255),
  `status`      tinyint,
  PRIMARY KEY (`openid`),
  index `idx_nickname` (`nickname`),
  index `idx_real_name` (`real_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
