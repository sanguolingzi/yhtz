package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.business.order.model.ConvertModel;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.service.ProductFresherInfoService;
import com.yinhetianze.business.product.service.ProductFresherRewardInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.pojo.product.ProductFresherPojo;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;
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
public class AddConvertOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Autowired
    private ProductFresherRewardInfoService productFresherRewardInfoServiceImpl;

    @Autowired
    private ProductFresherInfoService productFresherInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ConvertModel convertModel=(ConvertModel)model;
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
        if(CommonUtil.isEmpty(busiCustomerPojo.getGameId())){
            throw new BusinessException("用户没有绑定游戏");
        }

        //查询收货地址
        BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        busiCustomerReceiveaddressPojo.setId(convertModel.getReceiveaddressId());
        busiCustomerReceiveaddressPojo.setCustomerId(tokenUser.getId());
        busiCustomerReceiveaddressPojo = customerReceiveaddressInfoServiceImpl.selectOne(busiCustomerReceiveaddressPojo);
        if(CommonUtil.isEmpty(busiCustomerReceiveaddressPojo)){
            throw new BusinessException("收货地址有误");
        }

        //是否有兑换资格
        ProductFresherRewardPojo productFresherRewardPojo=new ProductFresherRewardPojo();
        productFresherRewardPojo.setGameId(busiCustomerPojo.getGameId());
        productFresherRewardPojo.setHandleStatus((short)0);
        productFresherRewardPojo.setStatus((short)0);
        productFresherRewardPojo=productFresherRewardInfoServiceImpl.selectFresherReward(productFresherRewardPojo);
        if(CommonUtil.isEmpty(productFresherRewardPojo)){
            throw new BusinessException("您没有兑换资格，无法兑换商品");
        }

        //查询兑换商品
        ProductFresherPojo productFresherPojo=new ProductFresherPojo();
        productFresherPojo.setId(convertModel.getId());
        productFresherPojo.setIsShow((short)0);
        productFresherPojo.setDelFlag((short)0);
        productFresherPojo=productFresherInfoServiceImpl.selectOne(productFresherPojo);
        if(CommonUtil.isEmpty(productFresherPojo)){
            throw new BusinessException("兑换的商品信息不存在");
        }

        if(productFresherPojo.getProdStorage()<1){
            throw new BusinessException("兑换的商品库存不足");
        }

        if(productFresherPojo.getuPrice().compareTo(convertModel.getuPrice())!=0){
            throw new BusinessException("U币数量有误");
        }

        //账户信息
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectBankrollInfo(tokenUser.getId());
        if(busiCustomerBankrollPojo.getRewardAmount().compareTo(convertModel.getuPrice())==-1){
            throw new BusinessException("U币数量不足");
        }

        BigDecimal freight=new BigDecimal("0");
        if(CommonUtil.isNotEmpty(productFresherPojo.getFreight())){
            freight=productFresherPojo.getFreight();
        }
        BigDecimal sellPrice=productFresherPojo.getSellPrice();

        //订单的信息
        Map<String,Object> order=new HashMap<>();
        //订单详情
        Map<String, Object> orderDetail = new HashMap<>();


        //订单参数
        String ordersNo=CommonUtil.getSerialnumber();
        order.put("customerId",tokenUser.getId());
        order.put("orderType",0);
        order.put("orderSourse",convertModel.getOrderSourse());
        order.put("receiveAddressId",convertModel.getReceiveaddressId());
        order.put("receiver",busiCustomerReceiveaddressPojo.getReceiveName());
        order.put("address",busiCustomerReceiveaddressPojo.getRegionLocation()+busiCustomerReceiveaddressPojo.getCity()+busiCustomerReceiveaddressPojo.getAreaCounty()+busiCustomerReceiveaddressPojo.getAddress());
        order.put("phone",busiCustomerReceiveaddressPojo.getPhone());
        order.put("addressDefault",busiCustomerReceiveaddressPojo.getDefaultStatus());
        order.put("ordersNo",ordersNo);
        order.put("freight",freight);
        order.put("payAmount",0);
        order.put("number",1);
        order.put("totalAmount",sellPrice);
        order.put("isGameOrder",4);
        order.put("createTime",new Date());
        order.put("payTime",new Date());
        order.put("payType",5);
        order.put("orderStatus",2);
        order.put("payIntegral",0);
        order.put("integralAmount",0);
        order.put("giveIntegral",0);
        order.put("proAmount",sellPrice);
        order.put("rewardAmount",convertModel.getuPrice().multiply(sysPropertiesUtils.getDecimalValue("uRatio")));
        order.put("gameId",busiCustomerPojo.getGameId());
        order.put("shopId",productFresherPojo.getShopId());
        //更新兑换表用
        order.put("productFresherRewardId",productFresherRewardPojo.getId());

        //订单详情参数
        orderDetail.put("uPrice",convertModel.getuPrice());
        orderDetail.put("prodId",productFresherPojo.getId());
        orderDetail.put("prodTitle",productFresherPojo.getProdTitle());
        orderDetail.put("productName",productFresherPojo.getProdName());
        orderDetail.put("photoUrl",productFresherPojo.getProdImg());
        orderDetail.put("sellPrice",productFresherPojo.getSellPrice());
        orderDetail.put("number",1);
        orderDetail.put("productDistinction",5);
        orderDetail.put("productType",0);
        orderDetail.put("finalPrice",productFresherPojo.getSellPrice());
        orderDetail.put("productSpec",productFresherPojo.getProdSpeci());

        Map<String,Object> parment=new HashMap<>();
        parment.put("order",order);
        parment.put("orderDetail",orderDetail);
        int orderId=orderBusiServiceImpl.convertOrderBuy(parment);
        if(CommonUtil.isEmpty(orderId)){
            throw new BusinessException("兑换商品失败");
        }
        int[] orderIds=new int[]{orderId};
        return orderIds;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ConvertModel convertModel=(ConvertModel)model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(convertModel.getOrderSourse())){
            error.rejectNull("orderSourse",null,"下单方式");
        }
        if(CommonUtil.isEmpty(convertModel.getReceiveaddressId())){
            error.rejectNull("receiveaddressId",null,"收货地址ID");
        }
        if(CommonUtil.isEmpty(convertModel.getId())){
            error.rejectNull("id",null,"兑换商品ID");
        }
        if(CommonUtil.isEmpty(convertModel.getuPrice())){
            error.rejectNull("uPrice",null,"消费的U币");
        }
        return error;
    }
}
