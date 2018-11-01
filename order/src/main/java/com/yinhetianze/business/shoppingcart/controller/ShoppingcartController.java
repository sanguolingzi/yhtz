package com.yinhetianze.business.shoppingcart.controller;

import com.yinhetianze.business.shoppingcart.model.ShopcartModel;
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
public class ShoppingcartController extends RestfulController {

    //新增购物车信息
    @RequestMapping(value="shoppingcart/add",method= RequestMethod.POST)
    public Object addShopcart(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopcartModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addShopcartExecutor");
        return executor.execute(request, response, model);
    }

    //删除购物车信息
    @RequestMapping(value="shoppingcart/remove",method=RequestMethod.POST)
    public Object removeShopcart(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopcartModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("removeShopcartExecutor");

        return executor.execute(request, response,model);
    }

    //修改购物车信息
    @RequestMapping(value="shoppingcart/modify",method=RequestMethod.POST)
    public Object modifyShopcart(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopcartModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("modifyShopcartExecutor");
        return executor.execute(request, response,model);
    }

    //查询我的购物车信息并分页
    @RequestMapping(value="shoppingcart/findShopcartByCustomerId",method=RequestMethod.GET)
    public Object findShopcartByCustomerId(HttpServletRequest request, HttpServletResponse response,ShopcartModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findShopcartExecutor");
        return executor.execute(request, response, model);
    }

    //确认订单
    @RequestMapping(value="shoppingcart/confirm",method=RequestMethod.POST)
    public Object findShopcartByIds(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopcartModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findShopcartByIdsExecutor");
        return executor.execute(request, response, model);
    }

    //五折商品确认订单
    @RequestMapping(value="shoppingcart/activityConfirm",method=RequestMethod.GET)
    public Object activityConfirm(HttpServletRequest request, HttpServletResponse response,ShopcartModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("activityConfirmExecutor");
        return executor.execute(request, response, model);
    }

}
