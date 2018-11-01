package com.yinhetianze.business.task;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.task.TaskPojo;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.List;

/**
 * quartz定时任务工具类
 * @author Administrator
 *
 */
public class JobManager
{
    /**
     * 计划任务工厂
     */
    private static SchedulerFactory sf = null;
    
    /**
     * 获取计划任务工厂实例
     * 单例化
     * @return
     */
    public synchronized static SchedulerFactory getSchedulerFactory()
    {
        if (CommonUtil.isEmpty(sf))
        {
            sf = new StdSchedulerFactory();
        }
        return sf;
    }
    
    /**
     * 获取标准计划任务对象
     * @return
     * @throws Exception
     */
    public synchronized static Scheduler getStdScheduler() throws Exception 
    {
        return getSchedulerFactory().getScheduler();
    }

    /**
     * 启动任务计时器
     * @throws Exception
     */
    public synchronized static void startSchedule() throws Exception
    {
        getSchedulerFactory().getScheduler().start();
    }
    
    /**
     * 停止计划任务队列(终止定时器及所有运行中的任务)
     * @throws Exception
     */
    public static void shutdownStdScheduler() throws Exception
    {
        Scheduler sche = getStdScheduler();
        
        if (sche.isStarted())
        {
            sche.shutdown();
            LoggerUtil.info(JobManager.class, "定时器停止成功");
        }
        else
        {
            LoggerUtil.info(JobManager.class, "定时器已经停止");
        }
    }
    
    /**
     * 终止正在执行中的任务
     * @param taskName
     * @param taskGroup
     * @return
     * @throws Exception
     */
    public static boolean stopStdJob(String taskName, String taskGroup) throws Exception
    {
        Scheduler sche = getStdScheduler();
        
        JobKey jk = null; // 任务标志
        List<JobExecutionContext> jetList = sche.getCurrentlyExecutingJobs(); // 定时器中的绑定任务列表
        if (CommonUtil.isNotEmpty(jetList))
        {
            for (JobExecutionContext jet : jetList)
            {
                // 如果任务列表中的任务与指定的任务名称与任务组相匹配，则终止该任务
                if (jet.getJobDetail().getKey().toString().equals(taskGroup+"." +taskName))
                {
                    jk = jet.getJobDetail().getKey();
                    return sche.interrupt(jk);
                }
            }
        }
        return false;
    }
    
    /**
     * 从任务队列中移除任务
     * @param taskName
     * @param taskGroup
     * @return
     * @throws Exception
     */
    public static boolean deleteJob(String taskName, String taskGroup) throws Exception
    {
        return deleteJob(new JobKey(taskName, taskGroup));
    }
    
    /**
     * 移除任务重载方法
     * @param jobKey
     * @return
     * @throws Exception
     */
    public static boolean deleteJob(JobKey jobKey) throws Exception 
    {
        Scheduler sche = getStdScheduler();
        
        return sche.deleteJob(jobKey);
    }
    
    /**
     * 开始任务
     * @param task
     * @return
     * @throws Exception
     */
    public static boolean scheduleJob(TaskPojo task) throws Exception
    {
        Scheduler sche = getStdScheduler();

        try
        {
            Date d = bindTask(sche, task);
            if (CommonUtil.isEmpty(d))
            {
                return false;
            }
            return true;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * 绑定任务
     * 添加任务到计划任务队列中
     * @param sche
     * @param task
     * @return
     * @throws Exception
     */
    public static Date bindTask(Scheduler sche, TaskPojo task) throws Exception
    {
        if (CommonUtil.isNotEmpty(task))
        {
            // 任务实现类对象
            Class<? extends Job> cls = getClass(task);

            // 构建任务详情
            JobDetail detail = JobBuilder.newJob(cls).withIdentity(task.getTaskName(), task.getTaskGroup()).build();
            detail.getJobDataMap().put("taskId", task.getTaskId());

            // 构建任务触发器
            Trigger trigger = getJobTrigger(task);
            if (CommonUtil.isNotEmpty(trigger))
            {
                // 绑定任务
                return sche.scheduleJob(detail, trigger);
            }
        }

        return null;
    }

    /**
     * 任务触发器
     * @param task
     * @return
     */
    public static Trigger getJobTrigger(TaskPojo task)
    {
        Trigger trigger = null;
        // 定时任务
        if ("1".equals(String.valueOf(task.getTaskType())))
        {
            // 按照指定规则进行刷新
            trigger = TriggerBuilder.newTrigger()
                    .withSchedule(
                            SimpleScheduleBuilder
                                    .simpleSchedule()
                                    .withIntervalInMilliseconds(Long.valueOf(task.getTaskPeriod()))
                                    .repeatForever())
                    .build();
        }
        // 计划任务
        else if ("2".equalsIgnoreCase(String.valueOf(task.getTaskType())))
        {
            // 按照指定周期时间进行刷新
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(task.getTaskName(), task.getTaskGroup())
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getTaskCron()))
                    .build();
        }
        return trigger;
    }

    /**
     * 获取Job任务类的类全名
     * @param task
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends Job> getClass(TaskPojo task) throws Exception
    {
        // 执行任务的类对象
        Class<? extends Job> cls = null;

        // 通过id没有获取到class则通过taskClass获取
        if (CommonUtil.isNotEmpty(task)
                && CommonUtil.isNotEmpty(task.getTaskClass()))
        {
            cls = (Class<? extends Job>) Class.forName(task.getTaskClass());
            LoggerUtil.info(JobManager.class, "通过taskClass获取job成功：TaskClass={}！", new Object[]{task.getTaskClass()});
        }
        else
        {
            throw new Exception("没有通过类全名获取到Class信息！配置Job失败！TaskClass="+task.getTaskClass());
        }

        return cls;
    }
    
}
