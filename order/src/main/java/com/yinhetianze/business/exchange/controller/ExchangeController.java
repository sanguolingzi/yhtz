package com.yinhetianze.business.exchange.controller;

import com.yinhetianze.business.exchange.model.ExchangeModel;
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
@RequestMapping("order")
public class ExchangeController extends RestfulController {

    /**
     * 消费者申请售后
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/exchange/add",method= RequestMethod.POST)
    public Object addExchange(HttpServletRequest request, HttpServletResponse response, @RequestBody ExchangeModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addExchangeExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 商家审核
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/exchange/check",method= RequestMethod.POST)
    public Object checkExchange(HttpServletRequest request, HttpServletResponse response, @RequestBody ExchangeModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("checkExchangeExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 用户取消售后
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/exchange/cancel",method= RequestMethod.POST)
    public Object cancelExchange(HttpServletRequest request, HttpServletResponse response, @RequestBody ExchangeModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("cancelExchangeExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 用户发货
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/exchange/delivery",method= RequestMethod.POST)
    public Object deliveryExchange(HttpServletRequest request, HttpServletResponse response, @RequestBody ExchangeModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("deliveryExchangeExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 商家收货
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/exchange/collect",method= RequestMethod.POST)
    public Object collectExchange(HttpServletRequest request, HttpServletResponse response, @RequestBody ExchangeModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("collectExchangeExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 商家打款
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/exchange/money",method= RequestMethod.POST)
    public Object moneyExchange(HttpServletRequest request, HttpServletResponse response, @RequestBody ExchangeModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("moneyExchangeExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除售后单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/exchange/delete",method= RequestMethod.POST)
    public Object deleteExchange(HttpServletRequest request, HttpServletResponse response, @RequestBody ExchangeModel model){
        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("deleteExchangeExecutor");
        return executor.execute(request, response, model);
    }


    /*********************************************************************************************************/

    /**
     * 查询我提交的售后订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="exchange/findExchangeOrder",method= RequestMethod.GET)
    public Object findExchangeOrder(HttpServletRequest request, HttpServletResponse response,ExchangeModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findExchangeOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 根据ID查询售后订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="exchange/findExchangeById",method= RequestMethod.GET)
    public Object findExchangeById(HttpServletRequest request, HttpServletResponse response,ExchangeModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findExchangeByIdExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台查询售后订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="exchange/findBackExchange",method= RequestMethod.GET)
    public Object findBackExchange(HttpServletRequest request, HttpServletResponse response,ExchangeModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findBackExchangeExecutor");
        return executor.execute(request, response, model);
    }


}
