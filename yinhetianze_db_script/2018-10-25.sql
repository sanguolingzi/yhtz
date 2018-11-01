-- 以下脚本于2018-11-01 10：30执行 begin
-- 兑换券字典
INSERT INTO busi_sys_dictionary(dic_name, dic_value, dic_type, dic_description)
VALUES('首充礼包兑换券', '1', 'VOUCHER_TYPE', '兑换券类型');
INSERT INTO busi_sys_dictionary(dic_name, dic_value, dic_type, dic_description)
VALUES('中奖商品兑换券', '2', 'VOUCHER_TYPE', '兑换券类型');

-- 兑换券状态
INSERT INTO busi_sys_dictionary(dic_name, dic_value, dic_type, dic_description)
VALUES('未使用', '1', 'VOUCHER_STATUS', '兑换券状态');
INSERT INTO busi_sys_dictionary(dic_name, dic_value, dic_type, dic_description)
VALUES('已使用', '2', 'VOUCHER_STATUS', '兑换券状态');
INSERT INTO busi_sys_dictionary(dic_name, dic_value, dic_type, dic_description)
VALUES('已过期', '3', 'VOUCHER_STATUS', '兑换券状态');

-- 兑换券定义表
CREATE TABLE busi_voucher(
    id INT AUTO_INCREMENT PRIMARY KEY,
    type SMALLINT(2) NOT NULL COMMENT '兑换券类型，字典：VOUCHER_TYPE',
    name VARCHAR(100) NOT NULL COMMENT '兑换券名称',
    cust_id INT NOT NULL COMMENT '兑换券拥有者',
    prod_id INT COMMENT '兑换券绑定兑换商品ID',
    link VARCHAR(200) COMMENT '兑换券跳转链接',
    status SMALLINT(2) NOT NULL COMMENT '券状态：字典：VOUCHER_STATUS',
    del_flag SMALLINT(2) DEFAULT 0 COMMENT '是否删除，0未删除，已删除，默认0未删除',
    expire_time DATETIME COMMENT '过期时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '兑换券表';
-- 以上脚本于2018-11-01 10：30执行 end

-- 以下脚本于 2018-10-26 10：56执行 begin
--爆品专区
CREATE TABLE busi_sys_detonating(
    id INT AUTO_INCREMENT PRIMARY KEY,
    detonating_name VARCHAR(100) COMMENT '展示名称',
    photo_url VARCHAR(100) COMMENT '图片地址',
    product_id INT(11) COMMENT '关联的商品ID',
    is_show SMALLINT(2) DEFAULT 1 COMMENT '是否首页显示，0显示，1不显示，默认1不显示',
    sort SMALLINT(6) COMMENT '排序编号',
    del_flag SMALLINT(2) DEFAULT 0 COMMENT '是否删除，1已删除，0未删除，默认0未删除',
    create_user INT(11) COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user INT(11) COMMENT '更新操作人',
    update_time DATETIME COMMENT '更新时间'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '爆品专区表';
-- 以上脚本于 2018-10-26 10：56执行 end