package com.yinhetianze.business.task.schedule;

import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

public class MobileHomePageSchedule extends InterruptableJob
{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        // 可以是任何service bean
        CacheData cacheData = (CacheData) ApplicationContextFactory.getBean("mobileIndexCacheData");

        // 执行定时任务相关业务逻辑
        cacheData.refreshCache();

        System.err.println("THIS IS MOBILE_HOME_PAGE_SCHEDULE");
    }
}
