package com.yinhetianze.business.companyaudit;

import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import com.yinhetianze.business.companyaudit.model.BusiSysCompanyAuditModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luox
 * on 2018/3/28.
 */
@RestController()
@RequestMapping("shop")
public class ShopCompanyAuditController extends RestfulController
{
    /**
     * 修改企业审核记录
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/companyaudit/update")
    public Object addCompanyAuditExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysCompanyAuditModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCompanyAuditExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询最新企业审核结果
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/companyaudit/getNewestInfo")
    public Object getCompanyAuditInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysCompanyAuditModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCompanyAuditInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
}
