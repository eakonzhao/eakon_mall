#收货信息表
CREATE TABLE `mmall`.`mmall_shipping`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) DEFAULT NULL COMMENT '用户id',
  `receiver_name` VARCHAR(50) DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` VARCHAR(20) DEFAULT NULL COMMENT '收货人电话',
  `receiver_province` VARCHAR(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` VARCHAR(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` VARCHAR(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` VARCHAR(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` VARCHAR(6) DEFAULT  NULL COMMENT '邮编',
  `create_time` DATETIME DEFAULT NULL ,
  `update_time` DATETIME DEFAULT NULL ,
  PRIMARY KEY (`id`)
)ENGINE =InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET =utf8;