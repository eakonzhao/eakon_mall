#分类表
#在设计分类表的时候必须考虑到以后可以实现递归
#几个约定：
#1.当parent_id为零代表是根节点，递归到这里为止，即递归结束
#2.sort_order是为了分类排序预留的字段
#3.id和parent_id的关系：假如当某条记录，它的id=10000，parent_id为0，则说明它是根节点
#  即它为最顶层分类；假如有一条记录，它的id是20000，parent_id是10000，则说明它是id为10000
#  的子分类。
CREATE TABLE `mmall`.`mmall_category`(
  `id` INT(11) AUTO_INCREMENT COMMENT '分类id',
  `parent_id` INT(11) DEFAULT NULL COMMENT '父类别对应的id，如果为0则代表它是根节点，即一级类别',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '类别名称(如家用电器、母婴用品等)',
  `status` TINYINT(1) DEFAULT '1' COMMENT '类别状态 1-代表正常 2-已经废弃',
  `sort_order` INT(4) DEFAULT NULL COMMENT '排序编号;同一级类别展示顺序:如果数值相等则自然排序',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '最后修改时间(更新时间)',
  PRIMARY KEY (`id`)
)ENGINE =InnoDB AUTO_INCREMENT=100032 DEFAULT CHARSET =utf8;