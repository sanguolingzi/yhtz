CREATE TABLE `busi_product_sales` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `product_id` INT(15) DEFAULT NULL COMMENT '商品id',
  `init_sales` INT(20) DEFAULT NULL COMMENT '初始化销量',
  `increase_sales` INT(20) DEFAULT '0' COMMENT '增长销量',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=307 DEFAULT CHARSET=utf8;
