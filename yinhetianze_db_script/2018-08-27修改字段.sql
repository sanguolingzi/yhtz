ALTER TABLE busi_product_detail MODIFY COLUMN prod_speci VARCHAR(300);

-- 一下脚本于201808271457 执行 begin ----------------
-- 代理店铺拓展字段 begin ---------------
-- 开户名称
ALTER TABLE busi_shop_proxy
ADD COLUMN bank_holder VARCHAR(100) COMMENT '户主名称' AFTER bank;
-- 店铺品类
ALTER TABLE busi_shop_proxy
ADD COLUMN shop_cate VARCHAR(100) COMMENT '店铺品类' AFTER shop_address;
-- 商品品类
ALTER TABLE busi_shop_proxy
ADD COLUMN prod_cate VARCHAR(100) COMMENT '商品品类' AFTER shop_address;
-- 合同有效期
ALTER TABLE busi_shop_proxy
ADD COLUMN contract_time VARCHAR(100) COMMENT '合同有效期' AFTER optor_id;
-- 合同有效期
ALTER TABLE busi_shop_proxy
ADD COLUMN delivery_time VARCHAR(200) COMMENT '发货时间' AFTER contract_time;
-- 备注
ALTER TABLE busi_shop_proxy
ADD COLUMN remark VARCHAR(100) COMMENT '备注' AFTER optor_id;
-- 联系电弧字段超长修改
ALTER TABLE busi_shop_proxy
MODIFY COLUMN contact_phone VARCHAR(20) COMMENT '联系电话';
-- 联系电弧字段超长修改
ALTER TABLE busi_shop_proxy
MODIFY COLUMN bank_card_number VARCHAR(20) COMMENT '银行卡号';
-- 默认结算方式：0=银行卡
ALTER TABLE busi_shop_proxy
MODIFY COLUMN settlement_method SMALLINT(6) DEFAULT 0 COMMENT '结算方式，0=银行卡';
-- 代理店铺拓展字段 end ---------------
-- 以上脚本于201808271457 执行 end ----------------