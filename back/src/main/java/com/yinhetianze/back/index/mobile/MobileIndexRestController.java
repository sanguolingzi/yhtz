package com.yinhetianze.back.index.mobile;

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

/**
 * on 2018/04/19.
 */
@RestController("mobileIndexRestController")
@RequestMapping("back")
public class MobileIndexRestController extends RestfulController
{

    /**
     * 移动端首页
     * @return
     */
    @GetMapping(value = "/mobileIndex")
    public Object mobileIndexExecutor(HttpServletRequest request, HttpServletResponse response)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("mobileIndexExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null);
    }

    /**
     * 刷新首页缓存
     * @return
     */
    @GetMapping(value = "/refreshMobileIndex")
    public Object refreshMobileIndexExecutor(HttpServletRequest request, HttpServletResponse response
            ,String refresh)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("refreshMobileIndexExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,refresh);
    }

    /**
     * 移动端首页 公告
     * @return
     */
    @GetMapping(value = "/mobileIndexNotice")
    public Object mobileIndexNoticeExecutor(HttpServletRequest request, HttpServletResponse response
            , SpringBootFileModel springBootFileModel)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("mobileIndexNoticeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, springBootFileModel);
    }


    /**
     * 移动端首页
     * @return
     */
    @GetMapping(value = "/floor")
    public Object mobileIndexFloorExecutor(HttpServletRequest request, HttpServletResponse response
            , SpringBootFileModel springBootFileModel)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("mobileIndexFloorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, springBootFileModel);
    }

    /**
     * 移动端首页
     * @return
     */
    @GetMapping(value = "/mobileFloor")
    public Object mobileFloorExecutor(HttpServletRequest request, HttpServletResponse response)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("mobileFloorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null);
    }

    /**
     * 刷新首页缓存
     * @return
     */
    @GetMapping(value = "/refreshMobileFloor")
    public Object refreshMobileFloorExecutor(HttpServletRequest request, HttpServletResponse response
            ,String refresh)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("refreshMobileFloorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,refresh);
    }
}
