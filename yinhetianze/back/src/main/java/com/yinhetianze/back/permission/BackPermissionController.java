package com.yinhetianze.back.permission;

import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionPageModel;
import com.yinhetianze.systemservice.permission.model.BusiSysRoleperModel;
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
public class BackPermissionController extends RestfulController
{
    /**
     * 新增系统权限
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/permission/add")
    public Object addSysPermissionExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysPermissionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysPermissionExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改系统权限
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/permission/update")
    public Object updateSysPermissionExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysPermissionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysPermissionExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改 角色权限关系
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/roleper/update")
    public Object updateSysRoleperExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysRoleperModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysRoleperExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除系统权限
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/permission/delete")
    public Object deleteSysPermissionExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysPermissionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysPermissionExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 分页查询系统权限列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/permission/page")
    public Object getSysPermissionListExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysPermissionPageModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysPermissionListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改角色用 角色权限菜单数据集合
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/roleper/list")
    public Object getSysPermissionForUpdateExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysPermissionPageModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysPermissionForUpdateExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询系统角色拥有的权限关系
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/roleper/perlist")
    public Object getSysRolePerListExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysRoleperModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysRolePerListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

}
