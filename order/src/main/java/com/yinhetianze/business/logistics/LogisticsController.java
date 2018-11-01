package com.yinhetianze.business.logistics;

import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.business.logistics.model.LogisticsInformationModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("order/logistics")
public class LogisticsController extends RestfulController {

    /**
     * 快递鸟推送信息接收接口
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="pushReceive",method= RequestMethod.POST)
    public Object pushReceiveExchange(HttpServletRequest request, HttpServletResponse response){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("pushReceiveExchange");
        return executor.execute(request, response, null);
    }

    /**
     * 快递鸟物流订阅接口
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="subscription",method= RequestMethod.POST)
    public Object subscriptionExchange(HttpServletRequest request, HttpServletResponse response, @RequestBody LogisticsInformationModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("subscriptionExchange");
        return executor.execute(request, response, model);
    }

    /**
     * 查询物流信息 根据订单Id进行查询
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="query",method= RequestMethod.GET)
    public Object queryExchange(HttpServletRequest request, HttpServletResponse response,LogisticsInformationModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("queryExchange");
        return executor.execute(request, response, model);
    }

}
