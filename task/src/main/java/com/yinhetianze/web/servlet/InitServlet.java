package com.yinhetianze.web.servlet;

import com.yinhetianze.business.task.JobManager;
import com.yinhetianze.core.task.InitModuleManager;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by Administrator
 * on 2018/1/28.
 */
@WebServlet(loadOnStartup = 2, name = "initServlet", value = "")
public class InitServlet extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        // 初始化spring容器工具类
        ApplicationContextFactory.init(config.getServletContext());

        // 初始化方法执行
        InitModuleManager initModuleManager = (InitModuleManager) ApplicationContextFactory.getBean("initModuleManager");
        initModuleManager.initial(config);
    }

    @Override
    public void destroy()
    {
        try
        {
            JobManager.shutdownStdScheduler();
            LoggerUtil.info(InitServlet.class, "系统停止，结束定时任务计时器");
        }
        catch (Exception e)
        {
            LoggerUtil.error(InitServlet.class, e);
        }
    }
}
