ALTER TABLE busi_product ADD supply_price DECIMAL(10,2) COMMENT '供货价(商家提供建议价格)';

ALTER TABLE busi_product ADD retail_price DECIMAL(10,2) COMMENT '零售价(商家提供建议价格)';

ALTER TABLE busi_product ADD share_price DECIMAL(10,2) COMMENT '分享优惠价';

ALTER TABLE busi_product ADD promotion_price DECIMAL(10,2) COMMENT '推广优惠价';

ALTER TABLE busi_product_detail MODIFY COLUMN cost_price DECIMAL(12,2) COMMENT '供货价(商家提供建议价格)';

ALTER TABLE busi_product_detail MODIFY COLUMN member_price DECIMAL(12,2) COMMENT '零售价(商家提供建议价格)';