package com.yinhetianze.business.order.controller;

import com.yinhetianze.business.order.model.*;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.springmvc.controller.RestfulController;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Administrator
 * on 2018/1/28.
 */
@RestController
@RequestMapping("order")
public class OrderController extends RestfulController
{
    /**
     * 创建订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="add",method= RequestMethod.POST)
    public Object addOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 创建游戏订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="addGame",method= RequestMethod.POST)
    public Object addGameOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addGameOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询我的订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="findByCustomerId",method= RequestMethod.GET)
    public Object findOrder(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询订单详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="findOrderById",method= RequestMethod.GET)
    public Object findOrderById(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findOrderByIdExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="delete",method= RequestMethod.POST)
    public Object deleteOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("deleteOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 申请退款
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="refund",method= RequestMethod.POST)
    public Object refundOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("refundOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 取消退款
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="cancelPay",method= RequestMethod.POST)
    public Object cancelPay(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("cancelPayExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 取消订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="cancelOrder",method= RequestMethod.POST)
    public Object cancelOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("cancelOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 完成订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="completeOrder",method= RequestMethod.POST)
    public Object completeOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("completeOrderExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 订单付款
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="payOrder",method= RequestMethod.POST)
    public Object payOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("payOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 统计订单数量
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="countOrder",method= RequestMethod.GET)
    public Object countOrder(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("countOrderExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 审核订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="auditOrder",method= RequestMethod.POST)
    public Object auditOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("auditOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 更新审核的订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="updateAuditOrder",method= RequestMethod.POST)
    public Object updateAuditOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("updateAuditOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 统计当前用户订单数量和消费金额
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="countAmount",method= RequestMethod.GET)
    public Object countAmount(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("countAmountExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 计算支付成功后赠送的积分
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="paySuccess",method= RequestMethod.POST)
    public Object paySuccess(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("paySuccessExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询数据库中是否保存了openId
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="getOpenId",method= RequestMethod.GET)
    public Object getOpenId(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("getOpenIdExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 微信授权获取openId
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="wechatAuth",method= RequestMethod.GET)
    public Object wechatAuth(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("wechatAuthExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 微信授权需要的地址
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="wechatUrl",method= RequestMethod.GET)
    public Object wechatUrl(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("wechatUrlExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 微信支付后查询微信订单
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="wxPayResult",method= RequestMethod.GET)
    public Object wxPayResult(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("wxPayResultExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 微信支付后查询微信订单
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="settlementOrder",method= RequestMethod.GET)
    public Object settlementOrder(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("settlementOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改发票信息
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="updateTax",method= RequestMethod.POST)
    public Object updateTax(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("updateTaxExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 查询快递公司信息
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="getExpressCode",method= RequestMethod.GET)
    public Object getExpressCode(HttpServletRequest request, HttpServletResponse response, OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("getExpressCodeExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 一元购下单
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="oneYuanOrder",method= RequestMethod.POST)
    public Object oneYuanOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OneYuanModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("oneYuanOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 购买会员礼包
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="addMemberOrder",method= RequestMethod.POST)
    public Object addMemberOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OneYuanModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addMemberOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * U币兑换商品
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="addConvertOrder",method= RequestMethod.POST)
    public Object addConvertOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody ConvertModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addConvertOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 活动商品下单
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value="activityOrder",method= RequestMethod.POST)
    public Object activityOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody ActivityOrderModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("activityOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 兑换券下单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="addVoucherOrder",method= RequestMethod.POST)
    public Object addVoucherOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OneYuanModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addVoucherOrderExecutor");
        return executor.execute(request, response, model);
    }


    /**----------------------------------------------后台订单----------------------------------------------------------*/

    /**
     * 后台查询所有订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="back/findOrder",method= RequestMethod.GET)
    public Object findBackOrder(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findBackOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台查询商户订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="back/findShopOrder",method= RequestMethod.GET)
    public Object findShopOrder(HttpServletRequest request, HttpServletResponse response,OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findShopOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台订单发货
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="back/sendOrder",method= RequestMethod.POST)
    public Object sendOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("sendOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台订单发货
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="back/sendBackOrder",method= RequestMethod.POST)
    public Object sendBackOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("sendBackOrderExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 商家填写物流信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="back/updateLogistics",method= RequestMethod.POST)
    public Object updateLogistics(HttpServletRequest request, HttpServletResponse response,@RequestBody OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("updateLogisticsExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 根据结算单Id查询订单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="back/getSettleOrder",method= RequestMethod.GET)
    public Object getSettleOrder(HttpServletRequest request, HttpServletResponse response, OrderDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("getSettleOrderExecutor");
        return executor.execute(request, response, model);
    }


}
