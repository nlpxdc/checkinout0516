SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
  `openid`            varchar(50)      NOT NULL,
  `nickname`           varchar(50) NOT NULL,
  `gender` int(11) ,
  `avatar_url` varchar(255),
  PRIMARY KEY (`openid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
