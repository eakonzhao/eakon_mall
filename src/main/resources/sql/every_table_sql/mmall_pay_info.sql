#支付信息表
CREATE TABLE `mmall`.`mmall_pay_info`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) DEFAULT NULL COMMENT '用户id',
  `order_no` BIGINT(20) DEFAULT NULL COMMENT '订单号',
  `pay_platform` INT(10) DEFAULT NULL COMMENT '支付平台 1-支付宝 2-微信',
  `platform_number` VARCHAR(200) DEFAULT NULL COMMENT '支付平台支付流水号',
  `platform_status` VARCHAR(20) DEFAULT NULL COMMENT '支付平台回传的支付状态',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
)ENGINE =InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET =utf8;