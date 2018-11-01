-- pc首页文件名称
insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('pcHomeDataName','pc_home_page_data.json',1,3,'pc首页数据文件名称');

-- mobile,app首页文件名称
insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('mobileHomeDataName','mobile_home_page_data.json',1,3,'mobile首页数据文件名称');

--mobile 2级分类导购词
ALTER TABLE busi_product_category ADD COLUMN shopping_guide VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '二级分类导购词';


-- 2018-07-18------------
-- 推荐关系表 增加字段 ----
alter table busi_customer_recommend_relation add column relation_code varchar(500) comment '推荐关系层级code';
alter table busi_customer_recommend_relation add column partner_id int comment '所属合伙人id';


-- 商品详情增加图片上传存储路径
insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('goodsDetailImagePath','/goodsDetail',1,4,'商品详情图片上传路径');


-- 店铺装饰增加图片上传存储路径
insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('shopDecorateImagePath','/shopDecorate',1,5,'店铺装饰图片上传路径');



-- 2018-07-19----------------
-- 用户提现记录表增加 原因 字段
alter table busi_customer_drawrecord
add column reason varchar(200) comment '审核不通过原因 不通过状态必填';
-- 用户提现记录表增加 提现转账方式 0微信转账 1银行卡转账 字段
alter table busi_customer_drawrecord
add column pay_type SMALLINT comment '提现方式 0微信转账  1 银行卡转账';

-- 2018-07-20----------------
-- 用户表增加 是否合作商 字段
alter table busi_customer
add column is_partner SMALLINT COMMENT '是否合作商 0是  1不是'  DEFAULT 1



-- 2018-07-23------------------
-- 微信绑定 生成open_id的唯一索引 防止重复添加
alter table busi_customer_wechat
add constraint uk_open_id unique (open_id);


-- 2018-07-24------------------
-- 新增一个账户
alter table busi_customer_bankroll
add column reward_amount decimal(10,2) not null DEFAULT '0.00';


-- 系统参数 增加 提现时间间隔(分钟) 提现次数
insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('dayInterval','2',1,3,'提现间隔(天)');

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('drawCount','5',1,3,'提现次数限制');

-- 2018-07-25 ------------------
-- 微信绑定用户关系表  增加微信用户信息 ------------------
alter table busi_customer_wechat
add column headimg_url VARCHAR(300) comment '微信账户头像';

alter table busi_customer_wechat
add column sex SMALLINT comment '微信账户性别 1是男性，2是女性，0是未知';

alter table busi_customer_wechat
add column nick_name VARCHAR(50) comment '微信账户昵称';

-- 2018-07-30 ------------------
-- 楼层表  增加链接  ------------------
ALTER TABLE busi_sys_floor
ADD COLUMN link_markup SMALLINT COMMENT '链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情' ;

ALTER TABLE busi_sys_floor
ADD COLUMN link_parameter VARCHAR(200) COMMENT '链接参数';

ALTER TABLE busi_sys_floor
ADD COLUMN floor_link VARCHAR(200) COMMENT '楼层链接';

-- 创建新的手机楼层   ------------------
CREATE TABLE `busi_sys_mobile_floor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `create_user` INT(11) DEFAULT NULL,
  `update_user` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `sort` SMALLINT(6) DEFAULT '0' COMMENT '排序号',
  `link_markup` SMALLINT(6) DEFAULT '0' COMMENT '链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0',
  `link_parameter` VARCHAR(200) DEFAULT NULL COMMENT '链接参数',
  `mobile_floor_link` VARCHAR(200) DEFAULT NULL COMMENT '手机楼层链接',
  `mobile_floor_name` VARCHAR(100) DEFAULT NULL COMMENT '楼层名称',
  `is_show` SMALLINT(6) DEFAULT '0' COMMENT '0=显示 1=不显示',
  `photo_url` VARCHAR(200) DEFAULT NULL COMMENT '图片地址',
  `del_flag` SMALLINT(6) DEFAULT '0' COMMENT '0 正常 1 已删除',
  PRIMARY KEY (`id`)
)
ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='手机楼层';

-- 创建新的手机楼层明细   ------------------
CREATE TABLE `busi_sys_mobile_floor_detail` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `create_user` INT(11) DEFAULT NULL,
  `update_user` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `sort` SMALLINT(6) DEFAULT '0' COMMENT '排序号',
  `link_markup` SMALLINT(6) DEFAULT '0' COMMENT '链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0',
  `link_parameter` VARCHAR(200) DEFAULT NULL COMMENT '链接参数',
  `mobile_floor_detail_link` VARCHAR(200) DEFAULT NULL COMMENT '手机楼层内容链接',
  `mobile_floor_id` VARCHAR(100) DEFAULT NULL COMMENT '楼层ID',
  `photo_url` VARCHAR(200) DEFAULT NULL COMMENT '图片地址',
  `is_show` SMALLINT(6) DEFAULT '0' COMMENT '0=显示 1=不显示',
  `del_flag` SMALLINT(6) DEFAULT '0' COMMENT '0 正常 1 已删除',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='手机楼层内容';

alter table busi_customer_wechat
add column open_id_his varchar(100) comment '记录解绑的微信Openid' DEFAULT null;

-- 2018-07-31----------------------
-- 用户表 增加合伙人业绩考核线  会员推荐码  是否获得推荐奖励字段 ------------------
alter table busi_customer
add COLUMN proportion decimal(10,2) DEFAULT 0 comment '合伙人赠送比例';

alter table busi_customer
add COLUMN recommend_code varchar(100) DEFAULT null comment '会员推荐码';

alter table busi_customer
add COLUMN recommend_reward SMALLINT DEFAULT 0 comment '推荐奖励是否赠送 0 为赠送 1已赠送';

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('recommendLimitDay','10',1,3,'推荐奖励时间间隔(天)');

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('recommendLimitUser','3',1,3,'推荐奖励达到用户数');

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('recommendReward','5',1,3,'推荐奖励奖励额度(元)');

-- 2018-08-01 增加提现手续费字段----------------------
ALTER table busi_customer_drawrecord
add column service_charge decimal(10,2) DEFAULT 0 comment '提现手续费';

ALTER table busi_customer_drawrecord
add column final_amount decimal(10,2) not null comment '实际支付金额';

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('serviceCharge','3',1,3,'提现百分比(%)');

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('proportion','30',1,2,'合伙人初始赠送比例');

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('recommendPrefix','yhtz',1,2,'用户推荐码前缀');






-- 2018-08-03 用户表对gameid 增加唯一索引----------------------
alter table busi_customer
add constraint uk_game_id unique (game_id);

------- 创建一元专区-----------------------
CREATE TABLE `busi_product_one_area` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `create_user` INT(11) DEFAULT NULL,
  `update_user` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `p_title` VARCHAR(100) DEFAULT NULL COMMENT '商品标题',
  `p_subtitle` VARCHAR(100) DEFAULT NULL COMMENT '商品副标题',
  `prod_name` VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
  `market_price` DECIMAL(10,2) DEFAULT NULL COMMENT '市场价',
  `sell_price` DECIMAL(10,2) DEFAULT NULL COMMENT '商品销售价(展示)',
  `freight` DECIMAL(10,2) DEFAULT NULL COMMENT '邮费',
  `sort` SMALLINT(6) DEFAULT '0' COMMENT '排序号',
  `shop_id` INT(11) DEFAULT NULL  COMMENT '所属店铺',
  `product_img` VARCHAR(200) DEFAULT NULL COMMENT '商品主图',
  `prod_speci` VARCHAR(200) DEFAULT NULL COMMENT '商品规格json',
  `is_show` SMALLINT(6) DEFAULT '0' COMMENT '0=显示 1=不显示',
  `del_flag` SMALLINT(6) DEFAULT '0' COMMENT '0 正常 1 已删除',
  `prod_details` TEXT DEFAULT NULL COMMENT '商品详情',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='一元专区';


 ---- 2018-08-04 ---------------------
 ------- 创建会员专区-----------------------
 CREATE TABLE `busi_product_member_parcel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `create_user` INT(11) DEFAULT NULL,
  `update_user` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `parcel_title` VARCHAR(100) DEFAULT NULL COMMENT '礼包标题',
  `parcel_subtitle` VARCHAR(100) DEFAULT NULL COMMENT '礼包副标题',
  `parcel_name` VARCHAR(100) DEFAULT NULL COMMENT '礼包名称',
  `sell_price` DECIMAL(10,2) DEFAULT NULL COMMENT '商品销售价(展示)',
  `freight` DECIMAL(10,2) DEFAULT '0' COMMENT '邮费',
  `sort` SMALLINT(6) DEFAULT '0' COMMENT '排序号',
  `shop_id` INT(11) DEFAULT NULL  COMMENT '所属店铺',
  `parcel_img` VARCHAR(200) DEFAULT NULL COMMENT '礼包主图',
  `parcel_speci` VARCHAR(200) DEFAULT NULL COMMENT '礼包规格json',
  `is_show` SMALLINT(6) DEFAULT '0' COMMENT '0=显示 1=不显示',
  `del_flag` SMALLINT(6) DEFAULT '0' COMMENT '0 正常 1 已删除',
  `parcel_details` TEXT DEFAULT NULL COMMENT '礼包详情',
  `parcel_describe` VARCHAR(200) DEFAULT NULL COMMENT '礼包描述',
  `number` INT(11) DEFAULT NULL COMMENT '房卡数量',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='会员专区';

alter table busi_customer_bankroll
add column game_id int default null comment '游戏用户id 用于游戏用户未在商城注册时 记录的U币信息';


alter table busi_customer_bankroll
add constraint uk_bankroll_game_id unique (game_id);


 ---- 2018-08-06 ---------------------
 ------- 创建会员礼包滚动图片 -----------------------
 CREATE TABLE `busi_product_member_parcel_img` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sort` SMALLINT(6) DEFAULT '0' COMMENT '排序号',
  `member_parcel_id` INT(11) DEFAULT NULL  COMMENT '所属礼包',
  `parcel_img` VARCHAR(200) DEFAULT NULL COMMENT '礼包图片',
  `del_flag` SMALLINT(6) DEFAULT '0' COMMENT '0 正常 1 已删除',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='会员礼包图片';


  ------- 创建一元专区内商品滚动图片 -----------------------
  CREATE TABLE `busi_product_one_area_img` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sort` SMALLINT(6) DEFAULT '0' COMMENT '排序号',
  `one_area_id` INT(11) DEFAULT NULL  COMMENT '所属专区',
  `product_img` VARCHAR(200) DEFAULT NULL COMMENT '商品图片',
  `del_flag` SMALLINT(6) DEFAULT '0' COMMENT '0 正常 1 已删除',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='专区内商品图片';


alter table busi_customer_bankroll_flow
add column update_time datetime default null comment '游戏用户绑定已有帐号 修改流水的操作时间';

---- 2018-08-04 创建会员权益信息表---------------------
CREATE TABLE `busi_sys_member_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `create_user` INT(11) DEFAULT NULL,
  `update_user` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `member_title` VARCHAR(100) DEFAULT NULL COMMENT '权益标题',
  `member_content` VARCHAR(100) DEFAULT NULL COMMENT '权益内容',
  `sort` SMALLINT(6) DEFAULT 0 COMMENT '排序号',
  `member_img` VARCHAR(200) DEFAULT NULL COMMENT '权益相关图片',
  `member_banner` VARCHAR(200) DEFAULT NULL COMMENT '权益相关banner',
  `is_show` SMALLINT(6) DEFAULT 0 COMMENT '0=显示 1=不显示',
  `del_flag` SMALLINT(6) DEFAULT 0 COMMENT '0 正常 1 已删除',
  `parent_id` INT(11) DEFAULT NULL COMMENT '所属权益',
  `member_level` SMALLINT(6) DEFAULT 0 COMMENT '权益等级',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='会员权益';

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('memberImg','/memberImg',1,3,'会员权益图片');


insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('memberBanner','/memberBanner',1,3,'会员权益banner');
 ---- 2018-08-07 ---------------------
 ------- 创建跳转链接码表 -----------------------
  CREATE TABLE `busi_sys_concatenated_code` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `is_show` SMALLINT(6) DEFAULT '0' COMMENT ' 0 显示  1 不显示',
  `concatenated_name` VARCHAR(100) DEFAULT NULL  COMMENT '名称',
  `jump_link` VARCHAR(200) DEFAULT NULL COMMENT '跳转链接',
  `is_parameter` SMALLINT(6) DEFAULT '0' COMMENT ' 0 无参数  1 有参数',
  `link_prompt` VARCHAR(200) DEFAULT NULL COMMENT '连接参数提示',
  `del_flag` SMALLINT(6) DEFAULT '0' COMMENT '0 正常 1 已删除',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='跳转链接码表';


-------------------- 2018-08-08 以上 脚本已执行 ---------------------------------------


------------2018-08-09-----------------

------------ 以上2018-08-14 17:38 执行 begin -----------------------------
-------新增自营店铺----------------------
CREATE TABLE `busi_shop_proxy` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `shop_account` VARCHAR(11) DEFAULT NULL COMMENT '商家账号',
  `shop_password` VARCHAR(100) DEFAULT NULL COMMENT '商家密码',
  `account_name` VARCHAR(100) DEFAULT NULL COMMENT '商家姓名',
  `shop_name` VARCHAR(100) DEFAULT NULL COMMENT '商家名称',
  `shop_logo` VARCHAR(200) DEFAULT NULL COMMENT '店铺LOGO',
  `contact_phone` VARCHAR(11) DEFAULT NULL COMMENT '联系电话',
  `shop_province` VARCHAR(100) DEFAULT NULL COMMENT '商家地省级ID',
  `shop_city` VARCHAR(100) DEFAULT NULL COMMENT '商家地市级ID',
  `shop_area` VARCHAR(100) DEFAULT NULL COMMENT '商家地区级ID',
  `shop_address` VARCHAR(100) DEFAULT NULL COMMENT '详细地址',
  `shop_code` VARCHAR(100) DEFAULT NULL COMMENT '邮编',
  `shop_phone` VARCHAR(11) DEFAULT NULL COMMENT '商家电话',
  `shop_email` VARCHAR(100) DEFAULT NULL COMMENT '联系邮箱',
  `settlement_method` SMALLINT(6) DEFAULT NULL COMMENT '结算方式 0 银行卡',
  `bank` VARCHAR(100) DEFAULT NULL COMMENT '开户银行',
  `bank_card_number` VARCHAR(19) DEFAULT NULL COMMENT '银行卡号',
  `customer_id` INT(11) DEFAULT NULL COMMENT '关联的用户ID',
  `optor_id` INT(11) DEFAULT NULL COMMENT '关联的管理员ID',
  `create_user` INT(11) DEFAULT NULL,
  `update_user` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `is_valid` SMALLINT(6) DEFAULT 0 COMMENT '0=有效 1=失效',
  `del_flag` SMALLINT(6) DEFAULT 0 COMMENT '0 正常 1 已删除',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='自营店铺';


alter table busi_customer_bankroll
add column used_reward_amount decimal(10,2) default 0 comment '累计 U币 消费金额';


insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('regeisterUAmount','1',1,2,'注册赠送U币额度');


insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('recommendUAmount','3',1,2,'推荐注册赠送U币额度');

alter table busi_customer_wechat
add column id_type SMALLINT default 1 comment '和用户绑定的openId类型 1 h5  2 app';

alter table busi_customer_wechat
add column id_type_his SMALLINT default null comment '记录解绑用户的openId类型 1 h5  2 app 防止主键冲突';


DROP INDEX wechat_customer ON busi_customer_wechat;

alter table busi_customer_wechat
add constraint uk_wechat_customer unique (customer_id,id_type);

---------------2018-08-14--------------
-------------增加会员表市场价 --------------
ALTER table busi_product_member_parcel
add column market_price DECIMAL(10,2) DEFAULT NULL COMMENT '市场价';

------------ 以上2018-08-14 17:38 执行 end -----------------------------

------------ 以下2018-08-15 17:03 执行 begin -----------------------------
------------- 添加跳转链接类型----------------
ALTER table busi_sys_concatenated_code
add column concatenated_type smallint(6) DEFAULT NULL COMMENT '跳转链接类型';
---------------删除是否添加链接参数------------------
ALTER TABLE busi_sys_concatenated_code
 DROP COLUMN is_parameter;
 ------------删除链接参数-----------------------
ALTER TABLE busi_sys_concatenated_code
 DROP COLUMN link_prompt;

------------ 以上2018-08-15 17:03 执行 end -----------------------------
---------------2018-08-15--------------

-------------- 以下脚本于20180820 18:19执行 begin -------------------------
-------------用户提现 增加标志 区分 h5 还是 app提现 --------------
ALTER table busi_customer_drawrecord
add column id_type SMALLINT COMMENT '1 h5  2 app';

ALTER table busi_customer_drawrecord
add column payment_no varchar(100) COMMENT '企业付款成功，返回的微信订单号';

ALTER table busi_customer_drawrecord
add column payment_time varchar(100) COMMENT '企业付款成功时间';

ALTER table busi_customer_drawrecord
add column err_code varchar(100) COMMENT '微信返回err_code';

ALTER table busi_customer_drawrecord
add column err_code_des varchar(100) COMMENT '微信返回错误代码描述';

ALTER table busi_customer_drawrecord
add column open_id varchar(100) COMMENT '企业付款所使用的openId';


CREATE TABLE `busi_customer_drawqueue` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `draw_id` INT(11) not null COMMENT '提现记录id',
  `draw_number` VARCHAR(50) DEFAULT NULL COMMENT '提现流水号',
  `id_type` smallint(6) DEFAULT NULL COMMENT '1 h5  2 app',
  `retry_count` smallint(6) DEFAULT 0 COMMENT '重试次数 3次以上 不处理',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_handle` SMALLINT(6) DEFAULT 0 COMMENT '0 未处理 1 已处理',
  `del_flag` SMALLINT(6) DEFAULT 0 COMMENT '0 正常 1 已删除',
  PRIMARY KEY (`id`)
)
 ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='微信提现待处理队列';

alter table busi_customer_drawqueue
add COLUMN error_desc varchar(100) comment "异常信息";

alter table busi_customer_drawqueue
add COLUMN error_code varchar(100) comment "异常信息code";

alter table busi_customer_drawqueue
add COLUMN status SMALLINT default 0 comment "0 正常  1 异常";

alter table busi_customer_drawqueue
add COLUMN handle_time DATETIME comment "队列处理时间";


---------------2018-08-20--------------
---------------修改字段允许为null 应对微信解绑功能--------------
ALTER TABLE busi_customer_wechat
 MODIFY COLUMN open_id VARCHAR(50) DEFAULT Null COMMENT '微信openId';

-------------- 以上脚本于20180820 18:19执行 end -------------------------