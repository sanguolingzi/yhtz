------------------------ 以下脚本已经于201808221750 执行 begin ---------------------
--------------------------- 会员礼包 ---------------------------------------------
ALTER table busi_product_member_parcel
add column parcel_price decimal(10,2) DEFAULT NULL COMMENT '销售展示价';


insert into busi_sys_sysproperties(p_name,p_value,is_work,p_module,p_description)
values('drawAmountBase','50',1,3,'提现金额基数');
------------------------ 以上脚本已经于201808221750 执行 end ---------------------