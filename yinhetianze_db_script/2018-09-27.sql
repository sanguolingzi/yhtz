-- 以下脚本于201809280935执行 begin
ALTER table busi_order_detail
add column u_price decimal(10,2) DEFAULT NULL COMMENT '商品可使用U币数量';
-- 以上脚本于201809280935执行 end

