package com.yinhetianze.business.initial;

import com.yinhetianze.business.task.JobManager;
import com.yinhetianze.business.task.service.TaskInfoService;
import com.yinhetianze.core.task.InitModuleHandler;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.task.TaskPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletConfig;
import java.util.List;

@Service
public class TaskInitialHandler implements InitModuleHandler
{
    @Autowired
    private TaskInfoService taskInfoServiceImpl;

    @Override
    public void init(ServletConfig config) throws Exception
    {
        LoggerUtil.info(TaskInitialHandler.class,"----------------------TaskInitialHandler init --------------------------");
        // 吧数据库中配置的任务项获取出来
        List<TaskPojo> taskList = taskInfoServiceImpl.getTaskPojoList(null);

        // 根据属性实例化job
        if (CommonUtil.isNotEmpty(taskList))
        {
            for (TaskPojo pojo : taskList)
            {
                // 只对isInitial为1和status为1的进行启动
                if (1 == pojo.getIsInitial() && 1 == pojo.getStatus())
                {
                    JobManager.scheduleJob(pojo);
                }

                // 启动任务
                JobManager.startSchedule();
            }
        }

        LoggerUtil.info(TaskInitialHandler.class,"----------------------TaskInitialHandler end --------------------------");
    }
}
