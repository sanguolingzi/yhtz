package com.yinhetianze.business.task.schedule;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesCacheData;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SysPropertiesSchedule extends InterruptableJob
{
    @Override
    public void execute(JobExecutionContext jobExecutionContext)  throws JobExecutionException
    {
//        //获取系统参数工具类
//        SysPropertiesUtils sysPropertiesUtils =(SysPropertiesUtils)ApplicationContextFactory.getBean("sysPropertiesUtils");
//        //获取redis的缓存数据
//        sysPropertiesUtils.getCacheData();
        //获取刷新缓存类
        SysPropertiesCacheData sysPropertiesCacheData = (SysPropertiesCacheData) ApplicationContextFactory.getBean("sysPropertiesCacheData");
        //刷新系统缓存数据
        sysPropertiesCacheData.refreshCache();
    }
}
