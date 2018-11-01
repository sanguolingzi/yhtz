-- 2018-09-11 begin ====================
INSERT INTO busi_sys_sysproperties (`p_name`, `p_value`, `del_flag`, `is_work`, `p_module`, `p_description`)
VALUES('ipTimeLimit','1','0','1','2','通过ip进行短信接口访问限制的时间(分钟)');
INSERT INTO busi_sys_sysproperties (`p_name`, `p_value`, `del_flag`, `is_work`, `p_module`, `p_description`)
VALUES('ipCountLimit','500','0','1','2','ipTimeLimit分钟内访问的次数');
INSERT INTO busi_sys_sysproperties (`p_name`, `p_value`, `del_flag`, `is_work`, `p_module`, `p_description`)
VALUES('ipTime','3','0','1','2','限制访问的时间(分钟)');
INSERT INTO busi_sys_sysproperties (`p_name`, `p_value`, `del_flag`, `is_work`, `p_module`, `p_description`)
VALUES('businessTimeLimit','3','0','1','2','通过ip+phone+busitype进行短信接口访问限制的时间(分钟)');
INSERT INTO busi_sys_sysproperties (`p_name`, `p_value`, `del_flag`, `is_work`, `p_module`, `p_description`)
VALUES('businessCountLimit','5','0','1','2','businessTimeLimit分钟内访问的次数');
-- -- 2018-09-11 已执行 end ====================