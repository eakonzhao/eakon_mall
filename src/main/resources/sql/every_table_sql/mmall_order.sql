#订单表
CREATE TABLE `mmall`.`mmall_order`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '订单表id',
  `order_no` BIGINT(20) DEFAULT NULL COMMENT '订单号',
  `user_id` INT(11) DEFAULT NULL COMMENT '用户id',
  `shipping_id` INT(11) DEFAULT NULL COMMENT '对应订单的地址',
  `payment` DECIMAL(20,2) DEFAULT NULL COMMENT '实际付款金额，单位是元 保留两位小数',
  `payment_type` INT(4) COMMENT '支付类型 1-在线支付',
  `postage` INT(10) DEFAULT NULL COMMENT '运费，单位元',
  `status` INT(10) DEFAULT NULL COMMENT '订单状态 0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭',
  `payment_time` DATETIME DEFAULT NULL COMMENT '支付时间',
  `send_time` DATETIME DEFAULT NULL COMMENT '发货时间',
  `end_time` DATETIME DEFAULT  NULL COMMENT '交易完成时间',
  `close_time` DATETIME DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
)ENGINE =InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET =utf8;