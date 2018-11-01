-- 以下脚本于 2018-10-20 10：00执行 begin
-------------- 用户收益日表-----------------------
DROP TABLE IF EXISTS busi_customer_earnings;
CREATE TABLE busi_customer_earnings(
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL COMMENT '收益所属人',
	  amount decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '收益额度',
    create_id INT NOT NULL COMMENT '收益产生人',
    create_time date NOT NULL COMMENT '创建时间 年月日'
) ENGINE = INNODB DEFAULT CHARSET UTF8 COMMENT '收益日表';

alter table busi_customer_earnings add unique index info(customer_id,create_id,create_time);

INSERT  INTO `busi_sys_task`(`task_name`,`task_group`,`task_class`,`task_type`,`task_period`,`task_cron`,`status`,`remarks`,`is_initial`,`create_time`,`update_time`)
VALUES ('余额收益定时任务','PRODUCT','com.yinhetianze.business.task.schedule.CustomerEaringSchedule',02,NULL,'0 0/1 * * * ?',01,'每日统计前一天的余额收益情况',01,'2018-10-12 9:30:00',NULL);

alter table busi_customer_recommend_relation
add INDEX grand_recom_id(grand_recom_customer_id);

--上下架活动商品定时器
INSERT  INTO `busi_sys_task`(`task_name`,`task_group`,`task_class`,`task_type`,`task_period`,`task_cron`,`status`,`remarks`,`is_initial`,`create_time`,`update_time`)
VALUES ('上下架活动商品定时器','PRODUCT','com.yinhetianze.business.task.schedule.ActivityProductStateSchedule',02,NULL,'0 0/1 * * * ?',01,'自动上下架活动商品',01,'2018-10-16 11:30:00',NULL)

--字典增加五折区活动
INSERT INTO busi_sys_dictionary(dic_name,dic_value,dic_type,dic_description) VALUES('五折区活动','1','ACTIVITY_TYPE','活动名称');
-- 以上脚本于 2018-10-20 10：00执行 end