#产品表
#主图默认取第一张子图
CREATE TABLE `mmall`.`mmall_product`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` INT(11) NOT NULL COMMENT '分类表id,对应mmall_category表的主键',
  `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `subtitle` VARCHAR(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` VARCHAR(500) DEFAULT null COMMENT '商品主图,存的是url相对地址 IP地址以常量的形式写在代码中',
  `sub_image` TEXT COMMENT '图片地址，json格式，拓展用',
  `detail` TEXT COMMENT '商品详情',
  `price` DECIMAL(20,2) NOT NULL COMMENT '价格，单位为元并保留小数',
  `stock` INT(11) NOT NULL COMMENT '库存数量',
  `status` INT(6) DEFAULT '1' COMMENT '商品状态 1-上架 2-下架 3-删除',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
)ENGINE =InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET =utf8;