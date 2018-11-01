package com.yinhetianze.business.task.schedule;

import com.yinhetianze.common.business.sys.errorcode.cachedata.ErrorCodeCacheData;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesCacheData;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 刷新错误码定时器
 */
public class ErrorCodeSchedule extends InterruptableJob
{
    @Override
    public void execute(JobExecutionContext jobExecutionContext)  throws JobExecutionException
    {
        //获取刷新错误码缓存类
        ErrorCodeCacheData errorCodeCacheData = (ErrorCodeCacheData) ApplicationContextFactory.getBean("errorCodeCacheData");
        //刷新错误码缓存数据
        errorCodeCacheData.refreshCache();
    }
}
