-- 一下脚本于 2018-10-15 15:12 执行 begin
DROP TABLE IF EXISTS busi_product_fresher;
CREATE TABLE busi_product_fresher(
    id INT AUTO_INCREMENT PRIMARY KEY,
    prod_title VARCHAR(100) NOT NULL COMMENT '商品标题',
    prod_subtitle VARCHAR(100) COMMENT '商品副标题',
    prod_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    prod_describe VARCHAR(200) COMMENT '商品描述',
    prod_details TEXT COMMENT '商品图文详情',
    u_price DECIMAL(10,2) NOT NULL COMMENT 'U币抵扣量',
    sell_price DECIMAL(10,2) DEFAULT 0 COMMENT '商品销售价',
    prod_img VARCHAR(200) COMMENT '商品主图',
    freight DECIMAL(10,2) COMMENT '运费',
    prod_storage INT(11) COMMENT '库存量',
    is_show SMALLINT(2) DEFAULT 1 COMMENT '是否首页显示，0显示，1不显示，默认1不显示',
    sort SMALLINT(6) COMMENT '排序编号',
    shop_id INT COMMENT '店铺ID',
    prod_speci VARCHAR(200) DEFAULT '{}' COMMENT '规格json',
    del_flag SMALLINT(2) DEFAULT 0 COMMENT '是否删除，1已删除，0未删除，默认0未删除',
    create_user INT(11) COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user INT(11) COMMENT '更新操作人',
    update_time DATETIME COMMENT '更新时间'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '新手专区商品表';

DROP TABLE IF EXISTS busi_product_fresher_img;
CREATE TABLE busi_product_fresher_img(
    id INT AUTO_INCREMENT PRIMARY KEY,
    prod_id INT NOT NULL COMMENT '关联商品ID',
    sort SMALLINT(6) DEFAULT 999 COMMENT '排序号',
    prod_img VARCHAR(200) COMMENT '商品图片',
    del_flag SMALLINT(6) DEFAULT 0 COMMENT '是否删除，0未删除，1已删除，默认0未删除'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '新手专区商品图片表';

DROP TABLE IF EXISTS busi_product_fresher_reward;
CREATE TABLE busi_product_fresher_reward(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cust_id INT NOT NULL COMMENT '用户id',
    STATUS SMALLINT(2) DEFAULT 0 COMMENT '是否兑换了奖励，0未兑换，1已兑换，默认0未兑换',
    prod_id INT COMMENT '兑换的商品ID',
    exchange_u_price DECIMAL(10,2) COMMENT 'U币抵扣量',
    exchange_time DATETIME COMMENT '兑换时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE(cust_id)
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '新手专区商品兑换记录表';


--------------------------- U币兑换区规格 ---------------------------------------------
insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('productFresherSpeci','	[{"speciName":"商品类型","speciValue":"U币兑换商品","isCustomed":null}]',1,4,'U币兑换商品规格');

-- 以上脚本于 2018-10-15 15:12 执行 end

