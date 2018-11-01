package com.yinhetianze.back.index.pc;

import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.business.httprequest.SpringBootFileModel;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController("pcIndexRestController")
@RequestMapping("back")
public class PcIndexRestController extends RestfulController {
    /**
     * 刷新首页缓存
     * @return
     */
    @GetMapping(value = "/refreshPcIndex")
    public Object refreshMobileIndexExecutor(HttpServletRequest request, HttpServletResponse response
            , String refresh)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>) ApplicationContextFactory.getBean("refreshPcIndexExecutor");
            return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,refresh);
    }
    /**
     * PC端首页
     * @return
     */
    @GetMapping(value = "/pcIndex")
    public Object mobileIndexExecutor(HttpServletRequest request, HttpServletResponse response)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("pcIndexExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null);
    }

    /**
     * PC端首页 公告
     * @return
     */
    @GetMapping(value = "/pcIndexNotice")
    public Object mobileIndexNoticeExecutor(HttpServletRequest request, HttpServletResponse response
            , SpringBootFileModel springBootFileModel)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("pcIndexNoticeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, springBootFileModel);
    }

}
