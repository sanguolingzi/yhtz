package com.yinhetianze.back.user;

import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.systemservice.user.model.BusiSysOptorPageModel;
import com.yinhetianze.systemservice.user.model.BusiSysUpdatepasswordModel;
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
public class BackUserController extends RestfulController
{
    /**
     * 系统用户登录
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/login")
    public Object loginExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysOptorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("loginExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 系统用户修改登录密码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updatePassword")
    public Object updatePassword(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysUpdatepasswordModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updatePasswordExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    /**
     * 新增系统用户
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/sysoptor/add")
    public Object addSysOptorExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysOptorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysOptorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改系统用户
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/sysoptor/update")
    public Object updateSysOptorExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysOptorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysOptorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除系统用户
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/sysoptor/delete")
    public Object deleteSysOptorExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysOptorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysOptorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 分页查询系统用户列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/sysoptor/page")
    public Object getSysOptorListExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysOptorPageModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysOptorListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    //生成二维码，返回地址
    @GetMapping(value="/createQrcode")
    public Object createQrcode(HttpServletRequest request, HttpServletResponse response, BusiSysOptorPageModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("createQrcodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    //查询二维码参数是否存在
    @GetMapping(value="/getSceneId")
    public Object getSceneId(HttpServletRequest request, HttpServletResponse response, BusiSysOptorPageModel model)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSceneIdExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);

    }
}
