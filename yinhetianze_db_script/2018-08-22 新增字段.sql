------------------------- 以下脚本于2018008221754 执行 begin---------------------------
ALTER TABLE busi_order_settlement ADD tax_amount DECIMAL(10,2)  DEFAULT '0.00' COMMENT '扣税金额';

ALTER TABLE busi_order ALTER COLUMN settlement_id SET DEFAULT 0;

ALTER TABLE busi_order_settlement MODIFY  customer_id INT(10)  NULL;
------------------------- 以上脚本于2018008221754 执行 end---------------------------