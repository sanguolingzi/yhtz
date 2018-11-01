package com.yinhetianze.back.thirdpart;

import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  第三方接入
 */
@RequestMapping("3rdpart")
@RestController()
public class ThirdpartRestController extends RestfulController{

    /**
     * 查询游戏用户充值记录接口（反向接口）
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/recharge/his")
    public Object getRechargeExecutor(HttpServletRequest request, HttpServletResponse response, GameRecordModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getRechargeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询平台用户信息接口（反向接口）
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/customer/detail")
    public Object getCustomerDetail(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getCustomerDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询订单详情接口（反向接口）
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/order/details")
    public Object getOrderDetails(HttpServletRequest request, HttpServletResponse response, OrderDto model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getOrderDetailsExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 查询第三方游戏用户基本信息（正向接口）
     */
    @GetMapping(value = "/gameCustomer")
    public Object getGameCustomer(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getGameCustomerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 平台用户编号接收接口（反向接口）
     */
    @GetMapping(value = "/user/bind")
    public Object getUserBind(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getUserBindExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 平台消费通知第三方接口 （正向接口）
     */
    @GetMapping(value = "/consumeMessage")
    public Object getConsumeMessage(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getConsumeMessageExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 商城提供给游戏用户注册接口
     */
    @GetMapping(value = "/customer/reg")
    public Object getCustomerReg(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getCustomerRegExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 商城提供非游戏用户名密码登录接口
     *
     */
    @GetMapping(value = "/customer/check")
    public Object getCustomerCheck(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getCustomerCheckExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 商城提供给游戏预处理登录接口
     */
    @GetMapping(value = "/customer/login")
    public Object getCustomerLogin(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getCustomerLoginExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 商城提供给游戏用户推荐关系绑定接口
     */

    @GetMapping(value = "/customer/referrer")
    public Object getCustomerReferrer(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getCustomerReferrerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 游戏端做任务奖励U币接收端口
     */
    @GetMapping(value = "/customer/gameAmount")
    public Object getCustomerGameAmount(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getCustomerGameAmountExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 商城提供gameId跟customerId绑定关系接口
     */
    @GetMapping(value = "/customer/binding")
    public Object getCustomerBinding(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getCustomerBindingExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 商城查询用户U币总额，友旗币总额接口
     */
    @GetMapping(value = "/customer/bankroll")
    public Object getCustomerBankroll(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getCustomerBankrollExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 游戏Id绑定
     */
    @GetMapping(value = "/gameId/bind")
    public Object getGameIdBind(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getGameIdBindExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 游戏推荐关系接收
     */
    @GetMapping(value = "/gameId/recommendedBind")
    public Object getRecommendedBind(HttpServletRequest request, HttpServletResponse response, GameBusinessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getRecommendedBindExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
}


