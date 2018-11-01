package com.yinhetianze.back.menu;

import com.yinhetianze.systemservice.menu.model.BusiSysPerMenuModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luox
 * on 2018/3/28.
 */
@RestController()
@RequestMapping("back")
public class BackMenuController extends RestfulController
{
    /**
     * 新增系统权限菜单关系
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/permenu/add")
    public Object addSysPerMenuExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysPerMenuModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysPerMenuExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改系统权限菜单关系
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/permenu/update")
    public Object updateSysPerMenuExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysPerMenuModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysPerMenuExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    /**
     * 查询系统权限拥有的菜单关系
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/permenu/list")
    public Object getSysPerMenuListExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysPerMenuModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysPerMenuListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 权限菜单修改用 菜单列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/permenu/menulist")
    public Object getSysMenuForUpdateExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysPerMenuModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysMenuForUpdateExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
}
