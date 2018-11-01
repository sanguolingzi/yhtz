-- 以下脚本于 2018-10-20 10:02执行 begin
DROP TABLE IF EXISTS busi_activity_product;
CREATE TABLE busi_activity_product(
    id INT AUTO_INCREMENT PRIMARY KEY,
    prod_name VARCHAR(100) NOT NULL COMMENT '商品名称',
    prod_title VARCHAR(100) NOT NULL COMMENT '商品标题',
    prod_sub_title VARCHAR(100) NOT NULL COMMENT '商品副标题',
    act_id VARCHAR(6) NOT NULL COMMENT '活动编码，对应字典ACTIVITY_TYPE',
    act_name VARCHAR(100) COMMENT '活动名称',
    prod_img VARCHAR(200) COMMENT '商品主图',
    prod_details TEXT COMMENT '商品图文详情',
    sort SMALLINT(6) DEFAULT 999 COMMENT '排序标志',
    prod_storage INT DEFAULT 0 COMMENT '商品库存',
    sell_price DECIMAL(10,2) DEFAULT 0 COMMENT '销售价',
    market_price DECIMAL(10,2) DEFAULT 0 COMMENT '市场价',
    u_price INT DEFAULT 0 COMMENT 'U币抵扣量',
    freight decimal(10,2) DEFAULT NULL COMMENT '邮费',
    shop_id int(11) DEFAULT NULL COMMENT '店铺ID',
    prod_speci varchar(200) DEFAULT '{}' COMMENT '规格json',
    is_show SMALLINT(2) DEFAULT 1 COMMENT '是否首页显示，0显示，1不显示，默认不显示',
    del_flag SMALLINT(2) DEFAULT 0 COMMENT '是否删除，0未删除，1已删除，默认0不删除',
    is_sale SMALLINT(2) DEFAULT 3 COMMENT '0 上架 1 下架 2 售罄 3.待上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    is_auto_sale SMALLINT(2) DEFAULT 0 COMMENT '是否自动上架标志，0不自动，1自动，默认0不自动，为1自动上架时间不能为空',
    is_auto_off SMALLINT(2) DEFAULT 0 COMMENT '是否自动下架，0不自动，1自动，默认0不自动，为1时自动下架时间不能为空',
    auto_sale_time DATETIME COMMENT '自动上架时间，is_auto_sale为1时不能为空',
    auto_off_time DATETIME COMMENT '自动下架时间，is_auto_off为1时不能为空',
    create_user INT COMMENT '创建者ID',
    update_time DATETIME COMMENT '更新时间',
    update_user INT COMMENT '更新者ID'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '活动商品表';

DROP TABLE IF EXISTS busi_activity_product_img;
CREATE TABLE busi_activity_product_img(
    id INT AUTO_INCREMENT PRIMARY KEY,
    act_prod_id INT NOT NULL COMMENT '关联的活动商品的id',
    act_id INT NOT NULL COMMENT '关联的活动id',
    prod_img VARCHAR(200) NOT NULL COMMENT '商品图片连接地址',
    sort SMALLINT(6) DEFAULT 999 COMMENT '排序标志',
    del_flag SMALLINT(2) DEFAULT 0 COMMENT '是否删除，0未删除，1已删除，默认0未删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_user INT COMMENT '创建者ID',
    update_time DATETIME COMMENT '更新时间',
    update_user INT COMMENT '更新者ID'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '活动商品图片表';

INSERT INTO busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
VALUES('discountSpeci','[{"speciName":"商品类型","speciValue":"活动商品","isCustomed":null}]',1,4,'活动商品规格');
-- 以上脚本于 2018-10-20 10:02执行 end

-- 以下脚本于 2018-10-15 15:47执行 begin
--新手专区新增市场价
ALTER TABLE busi_product_fresher
ADD market_price DECIMAL(10,2)  DEFAULT '0.00' COMMENT '市场价';
-- 以上脚本于 2018-10-15 15:47执行 end