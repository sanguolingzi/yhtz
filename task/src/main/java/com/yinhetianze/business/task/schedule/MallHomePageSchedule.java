package com.yinhetianze.business.task.schedule;

import com.yinhetianze.core.task.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

public class MallHomePageSchedule extends InterruptableJob
{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        System.err.println("This is MALL_HOME_PAGE_SCHEDULE");
    }
}
