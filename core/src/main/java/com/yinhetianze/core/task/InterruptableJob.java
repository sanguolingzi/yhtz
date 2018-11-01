package com.yinhetianze.core.task;

import org.quartz.UnableToInterruptJobException;

public abstract class InterruptableJob implements org.quartz.InterruptableJob
{
    @Override
    public void interrupt() throws UnableToInterruptJobException
    {
    }
}
