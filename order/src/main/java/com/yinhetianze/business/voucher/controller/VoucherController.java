package com.yinhetianze.business.voucher.controller;

import com.yinhetianze.business.voucher.model.VoucherlModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("order/voucher")
public class VoucherController extends RestfulController{
    /**
     * 个人消费券列表查询
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseData getVoucherList(HttpServletResponse response, HttpServletRequest request, VoucherlModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getVoucherListExecutor");
        return executor.execute(request, response, model);
    }
}
