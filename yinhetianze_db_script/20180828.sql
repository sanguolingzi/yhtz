-- 以下脚本于201808311203执行 begin ========
alter table busi_customer_collector
add column relation_id_his int(11) DEFAULT null comment '记录删除的收藏id';

------------ 删除 原有content 字段
ALTER TABLE busi_customer_collector
DROP COLUMN content;

----------- 增加唯一索引 防止重复添加导致的错误 -------------------------
alter table busi_customer_collector
add constraint uk_union_id unique (relation_id,customer_id,c_type);

ALTER TABLE busi_customer_collector
 MODIFY COLUMN relation_id int(11) DEFAULT Null COMMENT '关联数据id';
-- 以上脚本于201808311203执行 end ========