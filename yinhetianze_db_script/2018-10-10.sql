-- 以下脚本于 2018-10-15 15:14 执行 begin
--  begin
alter table busi_customer_recommend_relation
add column grand_recom_customer_id int(11) default null comment '上上级推荐人';

alter table busi_customer_bankroll_flow
add column create_id int(11) default null comment '收益贡献用户id';

alter table busi_product_fresher_reward
add column regeister_time date comment '游戏端注册时间';

ALTER TABLE busi_product_fresher_reward DROP COLUMN cust_id;

alter table busi_product_fresher_reward
add column game_id int(11) not null comment 'gameId';

alter table busi_product_fresher_reward
add constraint uk_game_id unique (game_id);

alter table busi_product_fresher_reward
add column handle_status smallint default 1  comment '是否由定时任务处理  0是 1否 默认 1';

-- end
-- 以上脚本于 2018-10-15 15:14 执行 end