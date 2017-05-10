#订单明细表
#加入了一些冗余数据 提高查询效率
CREATE TABLE `mmall`.`mmall_order_item`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` INT DEFAULT NULL ,
  `order_no` BIGINT(20) DEFAULT NULL COMMENT '订单编号',
  `product_id` INT(11) DEFAULT NULL COMMENT '商品id',
  `product_name` VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
  `product_image` VARCHAR(500) DEFAULT NULL COMMENT '商品图标地址',
  `cuncurrent_unit_price` DECIMAL(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价。单位是元，保留两位小数',
  `quantity` INT(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` DECIMAL(20,2) DEFAULT NULL COMMENT '商品单价 单位是元 保留两位小数',
  `create_time` DATETIME DEFAULT NULL ,
  `update_time` DATETIME DEFAULT NULL ,
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE ,
  KEY `order_no_user_id_index` (`order_no`,`user_id`) USING BTREE
)ENGINE =InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET =utf8;