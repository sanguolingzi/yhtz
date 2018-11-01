package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.business.oneYuanOrder.service.OneYuanOrderInfoService;
import com.yinhetianze.business.order.model.OneYuanModel;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.service.OneAreaInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.pojo.order.OneYuanOrderPojo;
import com.yinhetianze.pojo.product.OneAreaPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OneYuanOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private OneAreaInfoService oneAreaInfoServiceImpl;

    @Autowired
    private OneYuanOrderInfoService oneYuanOrderInfoServiceImpl;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OneYuanModel oneYuanModel=(OneYuanModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //校验用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }
        //查询收货地址
        BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        busiCustomerReceiveaddressPojo.setId(oneYuanModel.getReceiveaddressId());
        busiCustomerReceiveaddressPojo.setCustomerId(tokenUser.getId());
        busiCustomerReceiveaddressPojo = customerReceiveaddressInfoServiceImpl.selectOne(busiCustomerReceiveaddressPojo);
        if(CommonUtil.isEmpty(busiCustomerReceiveaddressPojo)){
            throw new BusinessException("收货地址无效");
        }

        //一个人只能只能购买一次一元商品
        OneYuanOrderPojo oneYuanOrderPojo=new OneYuanOrderPojo();
        oneYuanOrderPojo.setCustomerId(tokenUser.getId());
        oneYuanOrderPojo.setType((short)1);
        oneYuanOrderPojo=oneYuanOrderInfoServiceImpl.selectOne(oneYuanOrderPojo);
        if(CommonUtil.isNotEmpty(oneYuanOrderPojo)){
            throw new BusinessException("您已经购买过一元商品，不能再次购买");
        }

        //订单的信息
        Map<String,Object> order=new HashMap<>();
        //订单详情
        Map<String, Object> orderDetail = new HashMap<>();
        //查询一元商品表
        OneAreaPojo oneAreaPojo=new OneAreaPojo();
        oneAreaPojo.setId(oneYuanModel.getOutsideId());
        oneAreaPojo=oneAreaInfoServiceImpl.selectOne(oneAreaPojo);
        if(CommonUtil.isEmpty(oneAreaPojo)){
            throw new BusinessException("传入的一元商品ID有误");
        }
        if(oneAreaPojo.getSellPrice().compareTo(BigDecimal.ONE)!=0){
            throw new BusinessException("商品不是一元商品");
        }

        BigDecimal freight=new BigDecimal("0");
        if(CommonUtil.isNotEmpty(oneAreaPojo.getFreight())){
            freight=oneAreaPojo.getFreight();
        }
        BigDecimal payAmount=freight.add(oneAreaPojo.getSellPrice());
        if(payAmount.compareTo(oneYuanModel.getPayAmount())!=0){
            throw new BusinessException("最终支付金额有误");
        }
        //订单参数
        String ordersNo=CommonUtil.getSerialnumber();
        order.put("customerId",tokenUser.getId());
        order.put("orderType",0);
        order.put("orderSourse",oneYuanModel.getOrderSourse());
        order.put("receiveAddressId",oneYuanModel.getReceiveaddressId());
        order.put("receiver",busiCustomerReceiveaddressPojo.getReceiveName());
        order.put("address",busiCustomerReceiveaddressPojo.getRegionLocation()+busiCustomerReceiveaddressPojo.getCity()+busiCustomerReceiveaddressPojo.getAreaCounty()+busiCustomerReceiveaddressPojo.getAddress());
        order.put("phone",busiCustomerReceiveaddressPojo.getPhone());
        order.put("addressDefault",busiCustomerReceiveaddressPojo.getDefaultStatus());
        order.put("ordersNo",ordersNo);
        order.put("freight",freight);
        order.put("payAmount",payAmount);
        order.put("number",1);
        order.put("totalAmount",payAmount);
        order.put("isGameOrder",3);
        order.put("createTime",new Date());
        order.put("orderStatus",1);
        order.put("payIntegral",0);
        order.put("integralAmount",0);
        order.put("giveIntegral",0);
        order.put("discount",oneAreaPojo.getMarketPrice().subtract(oneAreaPojo.getSellPrice()));
        order.put("proAmount",oneAreaPojo.getSellPrice());
        order.put("rewardAmount",0);
        order.put("shopId",oneAreaPojo.getShopId());

        //订单详情参数
        orderDetail.put("prodId",oneAreaPojo.getId());
        orderDetail.put("prodTitle",oneAreaPojo.getpTitle());
        orderDetail.put("productName",oneAreaPojo.getProdName());
        orderDetail.put("photoUrl",oneAreaPojo.getProductImg());
        orderDetail.put("originalPrice",oneAreaPojo.getMarketPrice());
        orderDetail.put("sellPrice",oneAreaPojo.getSellPrice());
        orderDetail.put("number",1);
        orderDetail.put("productDistinction",3);
        orderDetail.put("productType",0);
        orderDetail.put("finalPrice",oneAreaPojo.getSellPrice());
        orderDetail.put("productSpec",oneAreaPojo.getProdSpeci());

        Map<String,Object> parment=new HashMap<>();
        parment.put("order",order);
        parment.put("orderDetail",orderDetail);

        int orderId=orderBusiServiceImpl.oneYuanBuy(parment);
        if(CommonUtil.isEmpty(orderId)){
            throw new BusinessException("创建一元订单失败");
        }
        int[] orderIds=new int[]{orderId};
        return orderIds;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OneYuanModel oneYuanModel=(OneYuanModel)model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(oneYuanModel.getOrderSourse())){
            error.rejectNull("orderSourse",null,"下单方式");
        }
        if(CommonUtil.isEmpty(oneYuanModel.getPayAmount())){
            error.rejectNull("payAmount",null,"付款金额");
        }
        if(CommonUtil.isEmpty(oneYuanModel.getOutsideId())){
            error.rejectNull("outsideId",null,"外部ID");
        }
        if(CommonUtil.isEmpty(oneYuanModel.getReceiveaddressId())){
            error.rejectNull("receiveaddressId",null,"收货地址ID");
        }
        return error;
    }
}
