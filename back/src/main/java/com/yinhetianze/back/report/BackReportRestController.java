package com.yinhetianze.back.report;

import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import com.yinhetianze.systemservice.report.model.ReportModel;
import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * on 2018/09/26.
 */
@RestController
@RequestMapping("back")
public class BackReportRestController  extends RestfulController {


    /**
     * 查询总退单数
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/sumExchange")
    public Object getSumExchangeExecutor(HttpServletRequest request, HttpServletResponse response, ReportModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getSumExchangeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询总退款金额
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/sumRefund")
    public Object getSumRefundExecutor(HttpServletRequest request, HttpServletResponse response, ReportModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getSumRefundExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询供应商总结算金额
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/sumSupplier")
    public Object getSumSupplierExecutor(HttpServletRequest request, HttpServletResponse response, ReportModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getSumSupplierExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询退单列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/exchangeList")
    public Object getExchangeListExecutor(HttpServletRequest request, HttpServletResponse response, ReportModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getExchangeListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

  /**
     * 报表查询总订单数量 跟今日订单数
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/report/totalOrder")
    public Object getTotalOrderExecutor(HttpServletRequest request, HttpServletResponse response){
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getTotalOrderExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response,null);
    }

    /**
     * 报表查询订单交易金额
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/report/totalAmount")
    public Object getTotalAmountOrderExecutor(HttpServletRequest request, HttpServletResponse response){
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getTotalAmountOrderExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null);
    }


    /**
     * 报表查询订单退款金额
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/report/totalRebateAmount")
    public Object getTotalRebateAmountExecutor(HttpServletRequest request, HttpServletResponse response){
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getTotalRebateAmountExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null);
    }

    /**
     * 报表查询总订单详情
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/report/totalOrderDetail")
    public Object getTotalOrderDetailExecutor(HttpServletRequest request, HttpServletResponse response){
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getTotalOrderDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response,null);
    }

    /**
     * 报表查询总交易额详情
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/report/totalAmountDetail")
    public Object getTotalAmountDetailExecutor(HttpServletRequest request, HttpServletResponse response){
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getTotalAmountDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response,null);
    }

    /**
     * 报表导出总订单详情
     */
    @GetMapping(value = "/report/totalOrderDetailReport")
    public Object getTotalOrderDetailReportExecutor(HttpServletRequest request, HttpServletResponse response){
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getTotalOrderDetailReportExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response,null);
    }

    /**
     * 报表查询订单退款金额详情
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/report/totalRebateDetailAmount")
    public Object getTotalRebateDetailAmountExecutor(HttpServletRequest request, HttpServletResponse response){
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getTotalRebateDetailAmountExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null);
    }
}
