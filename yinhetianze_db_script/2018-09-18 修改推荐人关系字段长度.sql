----------------- 以下脚本于20180918执行  begin
-- 修改字段长度 ----------------------
ALTER table busi_customer_recommend_relation
modify column relation_code varchar(1000);

-- 后台管理系统地址，发送代发店铺商家账号时告知登陆地址的配置信息
INSERT INTO busi_sys_sysproperties (`p_name`, `p_value`, `create_user`, `create_time`, `update_user`, `update_time`, `del_flag`, `is_work`, `p_module`, `p_description`)
VALUES('erp.address','https://www.youqiyp.com/erp','1','2018-09-14 15:56:10','1','2018-09-14 15:56:10','0','1','1','后台管理系统地址');
----------------- 以上脚本于20180918执行  end