ALTER TABLE busi_product
ADD flag_price DECIMAL(10,2)  DEFAULT '0.00' COMMENT '友旗币';

ALTER TABLE busi_product_detail
ADD u_price DECIMAL(10,2)  DEFAULT '0.00' COMMENT 'U币';

ALTER TABLE busi_order
ADD room_card_num DECIMAL(10,2) DEFAULT NULL COMMENT '游戏订单房卡数量';

ALTER TABLE busi_game_consume_message
ADD num int(11) DEFAULT NULL COMMENT '房卡数量';

ALTER TABLE busi_product_detail
ADD promotion_price DECIMAL(10,2)  DEFAULT '0.00' COMMENT '推广赚';

ALTER TABLE busi_product_detail
ADD flag_price DECIMAL(10,2)  DEFAULT '0.00' COMMENT '友旗币';

ALTER TABLE `busi_order_shoppingcart`
ADD UNIQUE ( customer_id,prod_id,prod_sku);

ALTER TABLE busi_product_detail
ADD number int(10)  DEFAULT '0' COMMENT '数量';
