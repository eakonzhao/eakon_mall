CREATE DATABASE `mmall`;

#用户表
CREATE TABLE `mmall`.`mmall_user`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户表自增id',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(50) NOT NULL COMMENT '用户密码，使用MD5加密',
  `email` VARCHAR(50) DEFAULT NULL COMMENT '用户邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '用户手机号',
  `question` VARCHAR(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` VARCHAR(100) DEFAULT NULL COMMENT '找回密码问题答案',
  `role` INT(4) NOT NULL COMMENT '用户角色 0-管理员 1-普通用户',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
)ENGINE =InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET =utf8;

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

#分类表
#在设计分类表的时候必须考虑到以后可以实现递归
#几个约定：
#1.当parent_id为零代表是根节点，递归到这里为止，即递归结束
#2.sort_order是为了分类排序预留的字段
#3.id和parent_id的关系：假如当某条记录，它的id=10000，parent_id为0，则说明它是根节点
#  即它为最顶层分类；假如有一条记录，它的id是20000，parent_id是10000，则说明它是id为10000
#  的子分类。
CREATE TABLE `mmall`.`mmall_user`(
  `id` INT(11) AUTO_INCREMENT COMMENT '分类id',
  `parent_id` INT(11) DEFAULT NULL COMMENT '父类别对应的id，如果为0则代表它是根节点，即一级类别',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '类别名称(如家用电器、母婴用品等)',
  `status` TINYINT(1) DEFAULT '1' COMMENT '类别状态 1-代表正常 2-已经废弃',
  `sort_order` INT(4) DEFAULT NULL COMMENT '排序编号;同一级类别展示顺序:如果数值相等则自然排序',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '最后修改时间(更新时间)',
  PRIMARY KEY (`id`)
)ENGINE =InnoDB AUTO_INCREMENT=100032 DEFAULT CHARSET =utf8;

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

