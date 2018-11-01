-- 以下脚本于2018-10-09 13:16执行 begin
ALTER table `busi_shop_proxy`
add column `open_id` varchar(50) DEFAULT NULL COMMENT '关联管理员的openId';
-- 以下脚本于2018-10-09 13:16执行 end