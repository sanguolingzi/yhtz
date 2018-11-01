package com.yinhetianze.business.order.executor;


import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.yinhetianze.business.alipay.Alipay;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.util.GetIp;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.business.wx.service.AppWxService;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Service
public class PayOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private WxService wxService;

    @Autowired
    private AppWxService appWxService;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto = (OrderDto) model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户信息不存在");
        }

        //校验用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }

        //查询账户信息
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectBankrollInfo(tokenUser.getId());
        if (CommonUtil.isEmpty(busiCustomerBankrollPojo)) {
            throw new BusinessException("没有获取到账户信息");
        }
        BigDecimal totalPayAmount = new BigDecimal("0");
        BigDecimal totalGiveIntegral = new BigDecimal("0");
        BigDecimal payIntegral = new BigDecimal("0");
        Integer isGameOrder=0;
        List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();
        List<Map<String,Object>> orderLists=new ArrayList<>();
        StringBuffer id = new StringBuffer();
        for (int j = 0; j < orderDto.getOrderIds().length; j++) {
            Map<String,Object> orderParment=new HashMap<>();
            orderParment.put("orderId",orderDto.getOrderIds()[j]);
            List<Map<String, Object>> orderList = orderInfoServiceImpl.findOrder(orderParment);
            if (CommonUtil.isEmpty(orderList)) {
                throw new BusinessException("传入的订单号有误");
            }
            Map<String, Object> map = orderList.get(0);
            if (Integer.valueOf(map.get("orderStatus") + "") != 1) {
                throw new BusinessException("订单已付款");
            }
            isGameOrder=Integer.valueOf(map.get("isGameOrder")+"");
            orderLists.add(orderList.get(0));
            BigDecimal payAmount = new BigDecimal(map.get("payAmount") + "");
            totalPayAmount = totalPayAmount.add(payAmount);
            totalGiveIntegral=totalGiveIntegral.add(new BigDecimal(map.get("giveIntegral") + ""));
            payIntegral=payIntegral.add(new BigDecimal(map.get("payIntegral") + ""));
            if(j==(orderDto.getOrderIds().length-1)){
                id.append(orderDto.getOrderIds()[j].toString());
            }else{
                id.append(orderDto.getOrderIds()[j].toString()+",");
            }
        }
        if (totalPayAmount.compareTo(orderDto.getPayAmount()) != 0) {
            throw new BusinessException("传入的支付金额有误");
        }
        if (orderDto.getPayType() == 4) {
            if (totalPayAmount.compareTo(busiCustomerBankrollPojo.getAmount()) == 1) {
                throw new BusinessException("支付金额应小于账户金额");
            }
            //校验支付密码
            if (CommonUtil.isEmpty(orderDto.getPayPassword())) {
                throw new BusinessException("支付密码不能为空");
            }
            //verifyPayPassword(orderDto.getCustomerId(),orderDto.getPayPassword(),customer.get("payPassword")+"");
            verifyPayPassword(tokenUser.getId(),orderDto.getPayPassword(),busiCustomerPojo.getPayPassword());
        }
            //积分支付或者余额支付
        try {
            if ( orderDto.getPayType() == 4) {
                Map<String,Object> result=new HashMap<>();
                //更改订单状态和账户积分
                orderBusiServiceImpl.payOrder(orderLists,4);
                result.put("isGameOrder",isGameOrder);
                result.put("totalPayAmount",totalPayAmount);
                result.put("totalGiveIntegral",totalGiveIntegral);
                result.put("payIntegral",payIntegral);
                return result;
            } else if (orderDto.getPayType() == 1) {  //支付宝支付
                String totalOrderNo=CommonUtil.getSerialnumber();
                Alipay alipay = new Alipay();
                String aliNotifyUrl=sysPropertiesUtils.getStringValue("aliNotifyUrl");
                String returnUrl=sysPropertiesUtils.getStringValue("aliReturnUrl");
                String frontUrl=sysPropertiesUtils.getStringValue("frontUrl");
                Map<String, Object> order = new HashMap<>();
                for (int j = 0; j < orderDto.getOrderIds().length; j++) {
                    order.put("orderId", orderDto.getOrderIds()[j]);
                    order.put("totalOrderNo", totalOrderNo);
                    orderBusiServiceImpl.updateOrder(order);
                }
                if(orderDto.getPayDevice()==2){      //pc端
                    alipay.aliPagePay(orderDto.getPayAmount() + "", totalOrderNo,aliNotifyUrl,frontUrl+"?payAmount="+totalPayAmount+"&totalGiveIntegral="+totalGiveIntegral+"&payIntegral="+payIntegral+"&isGameOrder="+isGameOrder,response);
                    return "success";
                }else if(orderDto.getPayDevice()==1){
                    alipay.alipay(orderDto.getPayAmount() + "", totalOrderNo, aliNotifyUrl,returnUrl+"?totalGiveIntegral="+totalGiveIntegral+"&totalPayAmount="+totalPayAmount+"&payIntegral="+payIntegral+"&isGameOrder="+isGameOrder,response);
                    return "success";
                }else{
                    Map<String,Object> aliMap=new HashMap<>();
                    aliMap.put("aliUrl",alipay.appPay(orderDto.getPayAmount() + "", totalOrderNo, aliNotifyUrl));
                    aliMap.put("isGameOrder",isGameOrder);
                    aliMap.put("totalPayAmount",totalPayAmount);
                    aliMap.put("totalGiveIntegral",totalGiveIntegral);
                    aliMap.put("payIntegral",payIntegral);
                    return  aliMap;
                }
            } else if (orderDto.getPayType() == 2) {      //微信支付
                String totalOrderNo=CommonUtil.getSerialnumber();
                Map<String, String> payInfo=new HashMap<>();
                if(orderDto.getPayDevice()==2){
                    WxPayUnifiedOrderRequest prepayInfo = WxPayUnifiedOrderRequest.newBuilder()
                            .outTradeNo(totalOrderNo)
                            .totalFee(orderDto.getPayAmount().multiply(new BigDecimal("100")).intValue())
                            .body("友旗有品")
                            .tradeType("NATIVE")
                            .spbillCreateIp(GetIp.getIp(request))
                            .productId(totalOrderNo)
                            .notifyURL( sysPropertiesUtils.getStringValue("notifyUrl"))
                            .build();
                    payInfo = wxService.getPayInfo(prepayInfo);
                }else if(orderDto.getPayDevice()==1){
                    if(orderDto.getIsWXDevice()==1){  //微信浏览器打开
                        BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
                        busiCustomerWechatPojo.setCustomerId( tokenUser.getId());
                        busiCustomerWechatPojo = customerWechatInfoServiceImpl.select(busiCustomerWechatPojo);

                        if (CommonUtil.isEmpty(busiCustomerWechatPojo.getOpenId())) {
                            throw new BusinessException("获取用户openId失败");
                        }
                        payInfo = wxService.getJSSDKPayInfo(busiCustomerWechatPojo.getOpenId(), totalOrderNo, orderDto.getPayAmount().multiply(new BigDecimal("100")).intValue() + "", "JSAPI", "玩客优品", GetIp.getIp(request), sysPropertiesUtils.getStringValue("notifyUrl"));
                    }else{
                        WxPayUnifiedOrderRequest prepayInfo = WxPayUnifiedOrderRequest.newBuilder()
                                .outTradeNo(totalOrderNo)
                                .totalFee(orderDto.getPayAmount().multiply(new BigDecimal("100")).intValue())
                                .body("友旗有品")
                                .tradeType("MWEB")
                                .spbillCreateIp(GetIp.getIp(request))
                                .sceneInfo("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://www.youqiyp.com/mobile\",\"wap_name\": \"友旗有品\"}} ")
                                .notifyURL( sysPropertiesUtils.getStringValue("notifyUrl"))
                                .build();
                        WxPayUnifiedOrderResult unifiedOrderResult = wxService.unifiedOrder(prepayInfo);
                        String prepayId = unifiedOrderResult.getPrepayId();
                        if (StringUtils.isBlank(prepayId)) {
                            throw new RuntimeException(String.format("无法获取prepay id，错误代码： '%s'，信息：%s。", unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes()));
                        } else {
                            String redirectUrl=sysPropertiesUtils.getStringValue("returnUrl")+"?totalOrderNo="+totalOrderNo+"&totalGiveIntegral="+totalGiveIntegral+"&payAmount="+totalPayAmount+
                                    "&payIntegral="+payIntegral+"&orderIds="+id.toString()+"&isGameOrder="+isGameOrder+"&token="+model.getToken();
                            payInfo.put("mwebUrl", unifiedOrderResult.getMwebUrl()+"&redirect_url=" + URLEncoder.encode(redirectUrl,"UTF-8"));
                        }
                    }
                }else{
                    WxPayUnifiedOrderRequest prepayInfo = WxPayUnifiedOrderRequest.newBuilder()
                            .outTradeNo(totalOrderNo)
                            .totalFee(orderDto.getPayAmount().multiply(new BigDecimal("100")).intValue())
                            .body("友旗有品")
                            .tradeType("APP")
                            .spbillCreateIp(GetIp.getIp(request))
                            .notifyURL(sysPropertiesUtils.getStringValue("notifyUrl"))
                            .build();
                    payInfo = appWxService.getPayInfo(prepayInfo);
                }
                Map<String, Object> order = new HashMap<>();
                for (int j = 0; j < orderDto.getOrderIds().length; j++) {
                    order.put("orderId", orderDto.getOrderIds()[j]);
                    order.put("totalOrderNo", totalOrderNo);
                    orderBusiServiceImpl.updateOrder(order);
                }
                payInfo.put("isGameOrder",isGameOrder+"");
                payInfo.put("totalOrderNo",totalOrderNo);
                payInfo.put("totalPayAmount",totalPayAmount+"");
                payInfo.put("totalGiveIntegral",totalGiveIntegral+"");
                payInfo.put("payIntegral",payIntegral+"");
                return payInfo;
            } else {           //快捷
                return null;
            }
        } catch (Exception e) {
             LoggerUtil.error(PayOrderExecutor.class,e);
            throw new BusinessException("支付失败");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderDto = (OrderDto) model;
        ErrorMessage error = new ErrorMessage();
        if (CommonUtil.isEmpty(orderDto.getOrderIds())) {
            error.rejectNull("orderIds", null, "支付订单的ID数组");
        }
        if (CommonUtil.isEmpty(orderDto.getPayType())) {
            error.rejectNull("payType", null, "订单支付方式");
        }
        return error;
    }

    /**
     * @param customerId
     * @param payPassword 需要校验的支付密码
     * @param oldPassword 数据库中存的支付密码
     * @return
     * @throws BusinessException
     */
    public void verifyPayPassword(Integer customerId, String payPassword, String oldPassword) throws BusinessException {
        //校验支付密码
        String payPasswords = "valiDate" + customerId;
        HashMap<String, Object> verifyPassword = redisManager.getSerializeObject(payPasswords, HashMap.class);
        if (!oldPassword.equals(MD5CoderUtil.encode(MD5CoderUtil.encode(payPassword)))) {
            if (CommonUtil.isNotEmpty(verifyPassword)) {
                if (verifyPassword.get("lockState").equals("0")) {
                    if ((Integer) verifyPassword.get("count") == 4) {
                        verifyPassword.put("count", 5);
                        verifyPassword.put("lockState", "1");
                        redisManager.setSerializeObject(payPasswords, verifyPassword, 600000L);
                    } else {
                        verifyPassword.put("count", (Integer) verifyPassword.get("count") + 1);
                        redisManager.setSerializeObject(payPasswords, verifyPassword);
                    }
                } else {
                    throw new BusinessException("支付密码错误次数过多,请稍后再试");
                }
            } else {
                verifyPassword = new HashMap<String, Object>();
                verifyPassword.put("lockState", "0");
                verifyPassword.put("count", 1);
                verifyPassword.put("busiCode", "PAY");
                redisManager.setSerializeObject(payPasswords, verifyPassword);
            }
            throw new BusinessException("支付密码错误，请重新输入!");
        } else {
            if (CommonUtil.isNotEmpty(verifyPassword)) {
                if (verifyPassword.get("lockState").equals("1")) {
                    throw new BusinessException("支付密码错误次数过多,请稍后再试");
                } else {
                    redisManager.deleteValue(payPasswords);
                }
            }
        }
    }

}
