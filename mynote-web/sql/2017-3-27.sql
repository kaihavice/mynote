DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `sid`          VARCHAR(32)  NOT NULL,
  `user_name`    VARCHAR(100)          DEFAULT '',
  `password`     VARCHAR(100) NOT NULL,
  `created_time` DATETIME     NOT NULL,
  `modify_time`  DATETIME     NOT NULL,
  `avatar`       VARCHAR(100)          DEFAULT '',
  `mobile_phone` VARCHAR(20)  NOT NULL DEFAULT '',
  `status`       INT(11)      NOT NULL,
  `email`        VARCHAR(50)           DEFAULT NULL,
  `syncnoteTime` DATETIME              DEFAULT NULL,
  `access_token` VARCHAR(100)          DEFAULT NULL,
  PRIMARY KEY (`sid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `note`;
CREATE TABLE `note` (
  `note_id`       VARCHAR(100) NOT NULL,
  `content`       VARCHAR(100) DEFAULT NULL,
  `create_time`   DATETIME     NOT NULL,
  `modified_time` DATETIME     NOT NULL,
  `is_attach`     TINYINT(1)   DEFAULT NULL,
  `_deleted`      TINYINT(1)   DEFAULT NULL,
  `kind`          VARCHAR(50)  DEFAULT NULL,
  `folder_id`     VARCHAR(30)  DEFAULT NULL,
  `user_id`       VARCHAR(32)  DEFAULT NULL,
  PRIMARY KEY (`note_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `sid`           VARCHAR(100) NOT NULL,
  `file_type`     VARCHAR(100) NOT NULL,
  `spath`         VARCHAR(100) NOT NULL DEFAULT '',
  `create_time`   DATETIME     NOT NULL,
  `modified_time` DATETIME     NOT NULL,
  `_deleted`      TINYINT(1)            DEFAULT NULL,
  `note_id`       VARCHAR(100)          DEFAULT NULL,
  `size`          BIGINT(20)            DEFAULT NULL,
  PRIMARY KEY (`sid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `folder`;

CREATE TABLE `folder` (
  `sid`            VARCHAR(100) NOT NULL,
  `note_id`        VARCHAR(100) NOT NULL,
  `name`           VARCHAR(100) DEFAULT NULL,
  `Color`          VARCHAR(100) NOT NULL,
  `create_time`    DATETIME     NOT NULL,
  `modified_time`  DATETIME     NOT NULL,
  `order`          MEDIUMTEXT,
  `sort_type`      MEDIUMTEXT,
  `deleted`        TINYINT(1)   DEFAULT NULL,
  `default_folder` TINYINT(1)   DEFAULT NULL,
  `isLock`         TINYINT(1)   DEFAULT NULL,
  PRIMARY KEY (`sid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `checklistItem`;

CREATE TABLE `checklistItem` (
  `sid`           VARCHAR(100) NOT NULL,
  `title`         VARCHAR(100) NOT NULL,
  `create_time`   DATETIME     NOT NULL,
  `modified_time` DATETIME     NOT NULL,
  `sort_order`    MEDIUMTEXT,
  `_deleted`      TINYINT(1)   DEFAULT NULL,
  `checked`       TINYINT(1)   DEFAULT NULL,
  `_status`       INT(11)      DEFAULT NULL,
  `note_id`       VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`sid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `table_seq`;
CREATE TABLE `table_seq` (
  `name`          VARCHAR(255) NOT NULL DEFAULT '1'
  COMMENT '表名',
  `current_value` INT(11)               DEFAULT NULL
  COMMENT '增长值',
  `_increment`    INT(11)      NOT NULL,
  PRIMARY KEY (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `table_seq` VALUES ('user', '1000017', '1');


CREATE TABLE member_token
(
  id            INTEGER      NOT NULL AUTO_INCREMENT,
  member_id     VARCHAR(255) NOT NULL
  COMMENT '会员id',
  android_token VARCHAR(50) COMMENT 'android token',
  ios_token     VARCHAR(50) COMMENT 'ios token',
  create_time   DATETIME     NOT NULL,
  update_time   DATETIME     NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DELIMITER $$

DROP FUNCTION IF EXISTS `nextval`$$

CREATE DEFINER =`root`@`%` FUNCTION `nextval`(seq_name VARCHAR(50))
  RETURNS INT(11)
  BEGIN
    DECLARE VALUE INTEGER;
    SET VALUE = 0;
    SELECT current_value
    INTO VALUE
    FROM table_seq
    WHERE NAME = seq_name;
    RETURN VALUE;
  END$$

DELIMITER ;

SELECT nextval('user');