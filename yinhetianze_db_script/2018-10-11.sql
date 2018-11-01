-- 以下脚本于 2018-10-15 15:16执行 begin
--系统参数增加新手专区的天数 和U币量
INSERT INTO busi_sys_sysproperties (p_name, p_value,  del_flag,is_work, p_module, p_description) VALUES('daysReward','7','0','1','2','新手专区任务时间');
INSERT INTO busi_sys_sysproperties (p_name, p_value,  del_flag,is_work, p_module, p_description) VALUES('uReward','100','0','1','2','	新手专区任务U币量');

--添加跳转链接
INSERT INTO busi_sys_concatenated_code(is_show,concatenated_name,jump_link,concatenated_type) VALUES('0','新手专区','请添加链接参数,如/noviceArea','11');

--修改新手专区兑换表状态定时器
INSERT  INTO `busi_sys_task`(`task_name`,`task_group`,`task_class`,`task_type`,`task_period`,`task_cron`,`status`,`remarks`,`is_initial`,`create_time`,`update_time`)
VALUES ('修改新手专区兑换表状态','PRODUCT','com.yinhetianze.business.task.schedule.FresherRewardStateSchedule',02,NULL,'0 0/30 * * * ?',01,'判断是否在规定时间内完成U币',01,'2018-10-11 9:30:00',NULL)
-- 以下脚本于 2018-10-15 15:16执行 end