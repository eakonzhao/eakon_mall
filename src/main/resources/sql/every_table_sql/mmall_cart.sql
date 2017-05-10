#购物车表
CREATE TABLE `mmall`.`mmall_cart`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '购物车表id',
  `user_id` INT(11) NOT NULL ,
  `product_id` INT(11) DEFAULT NULL COMMENT '商品id',
  `quantity` INT(11) DEFAULT NULL COMMENT '商品数量',
  `checked` INT(11) DEFAULT NULL COMMENT '是否选择 0-未勾选 1-已勾选',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
)ENGINE =InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET =utf8;