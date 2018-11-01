-- 以下脚本于201808311204执行 begin ================
-- 修改订单表的默认值
ALTER TABLE busi_order ALTER COLUMN reward_amount SET DEFAULT 0;
-- 以上脚本于201808311204执行 end ================