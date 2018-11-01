---------------------------------- 以下脚本于201808221752 执行 begin -------------
-- pc首页文件名称
insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('oneSpeci','	[{"speciName":"商品类型","speciValue":"一元商品","isCustomed":null}]',1,4,'一元商品规格');

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('memberSpeci','[{"speciName":"商品类型","speciValue":"会员礼包","isCustomed":null}]',1,4,'会员礼包规格');

insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('productType','1001',1,6,'通知游戏订单商品类型');

alter table busi_product alter column freight set default 0.00;
------------------------------------ 以上脚本于201808221752 执行 end ------------