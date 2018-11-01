package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.busi.CustomerWechatBusiService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.util.GetIp;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class WechatAuthExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private WxService wxService;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private CustomerWechatBusiService customerWechatBusiServiceImpl;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto)model;
        String totalOrderNo = CommonUtil.getSerialnumber();
        String state=request.getParameter("state");
        String[] arr=state.split("A");
        StringJoiner sb = new StringJoiner(",","","");
        Integer[] orderIds=new Integer[arr.length-1];
        BigDecimal payAmount=new BigDecimal("0");
        for(int i=0;i<arr.length;i++){
            if(i==arr.length-1){
                payAmount=new BigDecimal(arr[i]+"");
            }else{
                orderIds[i]=Integer.valueOf(arr[i]);
                sb.add(arr[i]);
            }
        }
        try{
            TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
            if(CommonUtil.isEmpty(tokenUser)){
                throw new BusinessException("用户未登陆");
            }
            Map<String, String> payInfo=new HashMap<>();
            //更改订单状态
            Map<String, Object> order = new HashMap<>();
            BigDecimal totalGiveIntegral = new BigDecimal("0");
            BigDecimal payIntegral = new BigDecimal("0");
            for (int j = 0; j < orderIds.length; j++) {
                Map<String,Object> orderParment=new HashMap<>();
                orderParment.put("orderId",orderIds[j]);
                List<Map<String, Object>> orderList = orderInfoServiceImpl.findOrder(orderParment);
                if (CommonUtil.isEmpty(orderList)) {
                    throw new BusinessException("传入的订单号有误");
                }
                Map<String, Object> map = orderList.get(0);
                totalGiveIntegral=totalGiveIntegral.add(new BigDecimal(map.get("giveIntegral") + ""));
                payIntegral=payIntegral.add(new BigDecimal(map.get("payIntegral") + ""));
                if (Integer.valueOf(map.get("orderStatus") + "") != 1) {
                    throw new BusinessException("订单号为" + orderIds[j] + "的订单已付款");
                }
                order.put("orderId", orderIds[j]);
                order.put("totalOrderNo", totalOrderNo);
                orderBusiServiceImpl.updateOrder(order);
            }
            //统一下单
            WxMpUser wxMpUser =  wxService.getWxUserInfoByCode(orderDto.getCode());
            payInfo = wxService.getJSSDKPayInfo(wxMpUser.getOpenId(), totalOrderNo, payAmount + "", "JSAPI", totalOrderNo, GetIp.getIp(request), sysPropertiesUtils.getStringValue("notifyUrl"));
            //把openId存入数据库

            BusiCustomerWechatPojo busiCustomerWechatPojo=new BusiCustomerWechatPojo();
            busiCustomerWechatPojo.setOpenId(wxMpUser.getOpenId());
            BusiCustomerWechatPojo busiCustomerWechatPojo1 = customerWechatInfoServiceImpl.select(busiCustomerWechatPojo);
            if(CommonUtil.isEmpty(busiCustomerWechatPojo1)){
                busiCustomerWechatPojo.setCustomerId(tokenUser.getId());
                busiCustomerWechatPojo.setIsRegeister((short)1);
                busiCustomerWechatPojo.setCustomerCode(UUID.randomUUID().toString().replaceAll("-", ""));
                customerWechatBusiServiceImpl.insertSelective(busiCustomerWechatPojo);
            }else{
                busiCustomerWechatPojo.setCustomerId(tokenUser.getId());
                busiCustomerWechatPojo.setId(busiCustomerWechatPojo1.getId());
                customerWechatBusiServiceImpl.updateByPrimaryKeySelective(busiCustomerWechatPojo);
            }
            payInfo.put("totalPayAmount",totalGiveIntegral+"");
            payInfo.put("totalPayAmount",payAmount.divide(new BigDecimal("100"))+"");
            payInfo.put("payIntegral",payIntegral+"");
            payInfo.put("orderIds",sb.toString());
            return payInfo;
        }catch (Exception e){LoggerUtil.error(WechatAuthExecutor.class,"微信授权异常! :"+e.toString());
            throw new BusinessException("微信授权异常");
        }

    }

}
