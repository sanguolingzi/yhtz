-- 以下脚本于 2018-10-20 10:06执行 begin
alter table busi_customer_recommend_relation
add column recom_game_id int(11) comment '推荐人gameId';

alter table busi_customer_recommend_relation
add column recomed_game_id int(11) comment '被推荐人gameId';

alter table busi_customer_recommend_relation
add column grand_recom_game_id int(11) comment '上上级推荐人gameId';

alter table busi_customer_recommend_relation add unique index info(recom_game_id,recomed_game_id);
-- 以上脚本于 2018-10-20 10:06执行 end
