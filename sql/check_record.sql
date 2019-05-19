SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for check_record
-- ----------------------------
DROP TABLE IF EXISTS `check_record`;
CREATE TABLE `check_record`
(
  `id`     int(11)     NOT NULL auto_increment,
  `openid` varchar(50) NOT NULL,
  `type`   tinyint     not null,
  `time`   datetime    not null default current_timestamp,
  PRIMARY KEY (`id`),
  index `idx_openid` (`openid`),
  index `idx_time` (`time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  auto_increment = 1;
