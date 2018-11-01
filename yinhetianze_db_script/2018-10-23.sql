-- 以下脚本于 2018-10-25 17-53 执行 begin
ALTER table busi_customer_recommend_relation
add column `show_id` int(11) DEFAULT NULL COMMENT '显示id';

ALTER table busi_customer_recommend_relation
add column `p_show_id` int(11) DEFAULT NULL COMMENT '推荐显示id';
-- 以上脚本于 2018-10-25 17-53 执行 end

