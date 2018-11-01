DROP TABLE IF EXISTS `busi_sys_intf_pattern`;

CREATE TABLE `busi_sys_intf_pattern` (
  `pattern` varchar(50) NOT NULL COMMENT '匹配的接口地址，以/开头，不以/结尾',
  `module` varchar(20) NOT NULL DEFAULT 'SYSTEM' COMMENT '所属模块',
  `permission` varchar(20) NOT NULL DEFAULT 'USER' COMMENT '需要对应权限才能访问，默认登陆用户访问',
  `status` smallint(2) unsigned zerofill DEFAULT '01' COMMENT '有效状态：1=有效，0=无效，默认1有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY `pattern` (`pattern`,`permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需要拥有特殊权限才能访问的接口地址配置表';