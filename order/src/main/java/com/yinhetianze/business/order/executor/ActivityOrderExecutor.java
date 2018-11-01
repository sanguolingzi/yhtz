package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.activity.service.info.ActivityProductInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.business.order.model.ActivityOrderModel;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.pojo.product.ActivityProductPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActivityOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private ActivityProductInfoService activityProductInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ActivityOrderModel activityOrderModel=(ActivityOrderModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }

        //查询收货地址
        BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        busiCustomerReceiveaddressPojo.setId(activityOrderModel.getReceiveaddressId());
        busiCustomerReceiveaddressPojo.setCustomerId(tokenUser.getId());
        busiCustomerReceiveaddressPojo = customerReceiveaddressInfoServiceImpl.selectOne(busiCustomerReceiveaddressPojo);
        if(CommonUtil.isEmpty(busiCustomerReceiveaddressPojo)){
            throw new BusinessException("收货地址有误");
        }

        //账户信息
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectBankrollInfo(tokenUser.getId());
        if(busiCustomerBankrollPojo.getRewardAmount().compareTo(activityOrderModel.getuPrice())==-1){
            throw new BusinessException("U币数量不足");
        }

        if(busiCustomerBankrollPojo.getStarCoin().compareTo(activityOrderModel.getPayIntegral())==-1){
            throw new BusinessException("友旗币数量不足");
        }

        ActivityProductPojo activityProductPojo=new ActivityProductPojo();
        activityProductPojo.setId(activityOrderModel.getId());
        activityProductPojo=activityProductInfoServiceImpl.selectOne(activityProductPojo);
        if(activityProductPojo.getProdStorage()<activityOrderModel.getNumber()){
            throw new BusinessException("商品库存不足");
        }

        BigDecimal freight=new BigDecimal("0");
        if(CommonUtil.isNotEmpty(activityProductPojo.getFreight())){
            if(activityProductPojo.getFreight().compareTo(activityOrderModel.getFreight())!=0){
                throw new BusinessException("运费有误");
            }
            freight=activityProductPojo.getFreight();
        }
        Integer uPriceNumber=activityProductPojo.getuPrice()*activityOrderModel.getNumber();
        if(activityOrderModel.getuPrice().compareTo(new BigDecimal(uPriceNumber))!=0){
            throw new BusinessException("U币数量有误");
        }
        BigDecimal sellPrice=activityProductPojo.getSellPrice();

        BigDecimal prodAmount=sellPrice.multiply(new BigDecimal(activityOrderModel.getNumber()));

        BigDecimal totalAmount=prodAmount.add(freight);

        BigDecimal uPriceAmount=activityOrderModel.getuPrice().multiply(sysPropertiesUtils.getDecimalValue("uRatio"));

        BigDecimal payIntegral=activityOrderModel.getPayIntegral();

        BigDecimal payAmount=totalAmount.subtract(payIntegral);

        if(totalAmount.compareTo(BigDecimal.ZERO)==-1||totalAmount.compareTo(activityOrderModel.getTotalAmount())!=0){
            throw new BusinessException("总金额有误");
        }

        if(payAmount.compareTo(BigDecimal.ZERO)==-1||payAmount.compareTo(activityOrderModel.getPayAmount())!=0){
            throw new BusinessException("支付金额有误");
        }

        //订单的信息
        Map<String,Object> order=new HashMap<>();
        //订单详情
        Map<String, Object> orderDetail = new HashMap<>();


        //订单参数
        String ordersNo=CommonUtil.getSerialnumber();
        order.put("customerId",tokenUser.getId());
        order.put("orderType",0);
        order.put("orderSourse",activityOrderModel.getOrderSourse());
        order.put("receiveAddressId",activityOrderModel.getReceiveaddressId());
        order.put("receiver",busiCustomerReceiveaddressPojo.getReceiveName());
        order.put("address",busiCustomerReceiveaddressPojo.getRegionLocation()+busiCustomerReceiveaddressPojo.getCity()+busiCustomerReceiveaddressPojo.getAreaCounty()+busiCustomerReceiveaddressPojo.getAddress());
        order.put("phone",busiCustomerReceiveaddressPojo.getPhone());
        order.put("addressDefault",busiCustomerReceiveaddressPojo.getDefaultStatus());
        order.put("ordersNo",ordersNo);
        order.put("freight",freight);
        order.put("payAmount",payAmount);
        order.put("number",activityOrderModel.getNumber());
        order.put("totalAmount",totalAmount);
        order.put("isGameOrder",5);
        order.put("payIntegral",payIntegral);
        order.put("integralAmount",payIntegral);
        order.put("giveIntegral",0);
        order.put("proAmount",prodAmount);
        order.put("rewardAmount",uPriceAmount);
        order.put("gameId",busiCustomerPojo.getGameId());
        order.put("shopId",activityProductPojo.getShopId());
        order.put("buyerMessage",activityOrderModel.getBuyerMessage());
        if(payAmount.compareTo(BigDecimal.ZERO)==0){
            order.put("orderStatus",2);
            order.put("payType",3);
            order.put("createTime",new Date());
            order.put("payTime",new Date());
            order.put("totalOrderNo", CommonUtil.getSerialnumber());
        }else{
            order.put("createTime",new Date());
            order.put("orderStatus",1);
        }

        //订单详情参数
        orderDetail.put("gameAmount",activityProductPojo.getuPrice());
        orderDetail.put("prodId",activityProductPojo.getId());
        orderDetail.put("originalPrice",activityProductPojo.getMarketPrice());
        orderDetail.put("prodTitle",activityProductPojo.getProdTitle());
        orderDetail.put("productName",activityProductPojo.getProdName());
        orderDetail.put("photoUrl",activityProductPojo.getProdImg());
        orderDetail.put("sellPrice",activityProductPojo.getSellPrice());
        orderDetail.put("number",activityOrderModel.getNumber());
        orderDetail.put("productDistinction",6);
        orderDetail.put("productType",0);
        orderDetail.put("finalPrice",activityProductPojo.getSellPrice());
        orderDetail.put("productSpec",activityProductPojo.getProdSpeci());

        Map<String,Object> parment=new HashMap<>();
        parment.put("order",order);
        parment.put("orderDetail",orderDetail);

        int orderId=orderBusiServiceImpl.activityOrderBuy(parment);
        if(CommonUtil.isEmpty(orderId)){
            throw new BusinessException("购买活动商品失败");
        }
        int[] orderIds=new int[]{orderId};
        return orderIds;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ActivityOrderModel activityOrderModel=(ActivityOrderModel)model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(activityOrderModel.getOrderSourse())){
            error.rejectNull("orderSourse",null,"下单方式");
        }
        if(CommonUtil.isEmpty(activityOrderModel.getReceiveaddressId())){
            error.rejectNull("receiveaddressId",null,"收货地址ID");
        }
        if(CommonUtil.isEmpty(activityOrderModel.getId())){
            error.rejectNull("id",null,"活动商品ID");
        }
        if(CommonUtil.isEmpty(activityOrderModel.getuPrice())){
            error.rejectNull("uPrice",null,"消费的U币");
        }
        if(CommonUtil.isEmpty(activityOrderModel.getFreight())){
            error.rejectNull("freight",null,"运费");
        }
        if(CommonUtil.isEmpty(activityOrderModel.getNumber())){
            error.rejectNull("number",null,"商品数量");
        }
        if(CommonUtil.isEmpty(activityOrderModel.getPayAmount())){
            error.rejectNull("payAmount",null,"支付金额");
        }
        if(CommonUtil.isEmpty(activityOrderModel.getTotalAmount())){
            error.rejectNull("totalAmount",null,"订单总金额");
        }
        if(CommonUtil.isEmpty(activityOrderModel.getPayIntegral())){
            error.rejectNull("payIntegral",null,"使用友旗币金额");
        }
        return error;
    }
}
