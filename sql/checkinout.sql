SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for checkinout
-- ----------------------------
DROP TABLE IF EXISTS `checkinout`;
CREATE TABLE `checkinout`
(
  `id`            int(11)      NOT NULL auto_increment,
  `openid`           varchar(50) NOT NULL,
  `type` int(11) not null ,
  `time` datetime not null ,
  PRIMARY KEY (`id`),
  index `idx_openid`(`openid`),
  index `idx_time`(`time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  auto_increment = 1;
