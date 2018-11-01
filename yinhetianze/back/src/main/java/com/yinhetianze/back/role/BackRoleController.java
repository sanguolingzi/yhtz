package com.yinhetianze.back.role;

import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.systemservice.role.model.BusiSysRolePageModel;
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
public class BackRoleController extends RestfulController
{
    /**
     * 新增系统角色
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/sysrole/add")
    public Object addSysRoleExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysRoleModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysRoleExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改系统角色
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/sysrole/update")
    public Object updateSysroleExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysRoleModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysRoleExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除系统角色
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/sysrole/delete")
    public Object deleteSysroleExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysRoleModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysRoleExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 分页查询系统角色列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/sysrole/page")
    public Object getSysRoleListExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysRolePageModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysRoleListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
}
