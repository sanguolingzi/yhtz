package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.logistics.service.info.LogisticsInformationInfoService;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shop.service.info.impl.ShopProxyInfoServiceImpl;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.order.LogisticsInformationPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FindOrderByIdExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private LogisticsInformationInfoService logisticsInformationInfoServiceImpl;

    @Autowired
    private ShopProxyInfoServiceImpl shopProxyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser.getId())){
            throw new BusinessException("没有获取到用户Id");
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String,Object> orderParment=new HashMap<>();
            orderParment.put("orderId",orderDto.getOrderId());
            orderParment.put("ordersNo",orderDto.getOrdersNo());
            if(CommonUtil.isNotEmpty(orderDto.getIsShop())){
                //校验商家信息
                BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
                if(CommonUtil.isEmpty(shopPojo)){
                    throw new BusinessException("该用户不是商家");
                }
                orderParment.put("shopId",shopPojo.getId());
            }else{
                orderParment.put("customerId",tokenUser.getId());
            }

            List<Map<String,Object>> orderList=orderInfoServiceImpl.findOrder(orderParment);
            if(CommonUtil.isEmpty(orderList)){
                throw new BusinessException("传入的订单号有误");
            }
            Map<String,Object> map=orderList.get(0);
            //未付款时，自动取消剩余时间
            if(Integer.valueOf(map.get("orderStatus")+"")==1){
                Date createTime=format.parse(map.get("createTime")+"");
                int cancelNonPayment=sysPropertiesUtils.getIntValue("cancelNonPayment");
                Long l=createTime.getTime()+(1000*60*60*cancelNonPayment)-new Date().getTime();
                int hours=0;
                int minutes=0;
                if(l>0){
                    hours = (int) (l/(1000 * 60 * 60));
                    minutes=(int)(l-1000 * 60 * 60*hours)/(1000 * 60);
                }
                if (hours==0){
                    map.put("remindCancelHours",minutes+"分");
                }else{
                    map.put("remindCancelHours",hours+"小时"+minutes+"分");
                }
            }
            //确认收货倒计时
            if(Integer.valueOf(map.get("orderStatus")+"")==3){
                Date deliveryTime=format.parse(map.get("deliveryTime")+"");
                int takeDeliveryDays=sysPropertiesUtils.getIntValue("takeDeliveryDays");
                Long l=deliveryTime.getTime()+(1000*60*60*24*takeDeliveryDays)-new Date().getTime();
                int day=0;
                int hours=0;
                int minutes=0;
                if(l>0){
                    day=(int) (l/(1000 * 60 * 60*24));
                    hours = (int) (l-1000 * 60 * 60*24*day)/(1000 * 60 * 60);
                    minutes=(int)(l-1000 * 60 * 60*24*day-1000 * 60 * 60*hours)/(1000 * 60);
                }
                if(day==0){
                    map.put("remindCompleteHours",hours+"小时"+minutes+"分");
                    if(hours==0){
                        map.put("remindCompleteHours",minutes+"分");
                    }
                }else{
                    map.put("remindCompleteHours",day+"天"+hours+"小时"+minutes+"分");
                }
            }

            map.put("logistics",null);
            if(Integer.valueOf(map.get("orderStatus")+"")>2&&Integer.valueOf(map.get("orderStatus")+"")<7){
                LogisticsInformationPojo logisticsInformationPojo=new LogisticsInformationPojo();
                logisticsInformationPojo.setOrdersId(orderDto.getOrderId());
                logisticsInformationPojo=logisticsInformationInfoServiceImpl.getLogisticsInformation(logisticsInformationPojo);
                if(CommonUtil.isNotEmpty(logisticsInformationPojo)&&CommonUtil.isNotEmpty(logisticsInformationPojo.getPushLogistics())){
                    List<Map<String,Object>> logisticsList=CommonUtil.readFromString(logisticsInformationPojo.getPushLogistics(),ArrayList.class);
                    map.put("logistics",logisticsList.get(logisticsList.size()-1));
                }
            }

            //查询店铺信息
            if(CommonUtil.isNotEmpty(map.get("proxyShopId"))){
                ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
                shopProxyPojo.setId(Integer.valueOf(map.get("proxyShopId")+""));
                shopProxyPojo = shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
                if(CommonUtil.isEmpty(shopProxyPojo)){
                    throw new BusinessException("没有相关店铺信息");
                }
                map.put("shopName",shopProxyPojo.getShopName());
                map.put("shopLogo",shopProxyPojo.getShopLogo());
            }else{
                BusiShopPojo busiShopPojo = new BusiShopPojo();
                busiShopPojo.setId(Integer.valueOf(map.get("shopId")+""));
                busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
                if(CommonUtil.isEmpty(busiShopPojo)){
                    throw new BusinessException("没有相关店铺信息");
                }
                map.put("shopName",busiShopPojo.getShopName());
                map.put("shopLogo",busiShopPojo.getShopLogo());
            }

            //消费者信息
            BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
            busiCustomerPojo.setId(Integer.valueOf(map.get("customerId")+""));
            busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
            if (CommonUtil.isEmpty(busiCustomerPojo)) {
                throw new BusinessException("没有获取到用户信息");
            }
            map.put("customerName",busiCustomerPojo.getPhone());
            map.put("orderDetailList",orderInfoServiceImpl.findOrderDetail(Integer.valueOf(map.get("id")+""),null));
            return map;
        }catch (Exception e){
            LoggerUtil.error(FindOrderByIdExecutor.class,e);
            throw new BusinessException("查询订单失败");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        OrderDto orderDto=(OrderDto)model;
        if(CommonUtil.isEmpty(orderDto.getOrderId())&&CommonUtil.isEmpty(orderDto.getOrdersNo())){
            error.rejectNull("orderId",null,"订单ID订单编号填一个");
        }
        return error;
    }
}
