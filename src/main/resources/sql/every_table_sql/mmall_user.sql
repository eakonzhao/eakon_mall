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