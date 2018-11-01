package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.business.oneYuanOrder.service.OneYuanOrderInfoService;
import com.yinhetianze.business.order.model.OneYuanModel;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.service.MemberParcelInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import com.yinhetianze.pojo.order.OneYuanOrderPojo;
import com.yinhetianze.pojo.product.MemberParcelPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class AddMemberOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Autowired
    private MemberParcelInfoService memberParcelInfoServiceImpl;

    @Autowired
    private OneYuanOrderInfoService oneYuanOrderInfoServiceImpl;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OneYuanModel oneYuanModel=(OneYuanModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //查询推荐人ID
        BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo=customerRecommendRelationInfoServiceImpl.selectRecommendUser(tokenUser.getGameId());
        //校验用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }
        //一个人只能只能购买一次一元商品
        OneYuanOrderPojo oneYuanOrderPojo=new OneYuanOrderPojo();
        oneYuanOrderPojo.setCustomerId(tokenUser.getId());
        oneYuanOrderPojo.setType((short)2);
        oneYuanOrderPojo=oneYuanOrderInfoServiceImpl.selectOne(oneYuanOrderPojo);
        if(CommonUtil.isNotEmpty(oneYuanOrderPojo)){
            throw new BusinessException("您已经购买过礼包，不能再次购买");
        }
        if(busiCustomerPojo.getIsMember()==0){
            throw new BusinessException("您已是会员，不能购买会员礼包");
        }

        //查询收货地址
        BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        busiCustomerReceiveaddressPojo.setId(oneYuanModel.getReceiveaddressId());
        busiCustomerReceiveaddressPojo.setCustomerId(tokenUser.getId());
        busiCustomerReceiveaddressPojo = customerReceiveaddressInfoServiceImpl.selectOne(busiCustomerReceiveaddressPojo);
        if(CommonUtil.isEmpty(busiCustomerReceiveaddressPojo)){
            throw new BusinessException("收货地址无效");
        }
        //查询礼包表
        MemberParcelPojo memberParcelPojo=new MemberParcelPojo();
        memberParcelPojo.setId(oneYuanModel.getOutsideId());
        memberParcelPojo=memberParcelInfoServiceImpl.selectOne(memberParcelPojo);
        if(CommonUtil.isEmpty(memberParcelPojo)){
            throw new BusinessException("传入的礼包ID有误");
        }

        BigDecimal freight=new BigDecimal("0");
        if(CommonUtil.isNotEmpty(memberParcelPojo.getFreight())){
            freight=memberParcelPojo.getFreight();
        }
        BigDecimal payAmount=freight.add(memberParcelPojo.getParcelPrice());
        if(payAmount.compareTo(oneYuanModel.getPayAmount())!=0){
            throw new BusinessException("最终支付金额有误");
        }
        if(CommonUtil.isNotEmpty(memberParcelPojo.getNumber())&&memberParcelPojo.getNumber()>0&&CommonUtil.isEmpty(busiCustomerPojo.getGameId())){
            throw new BusinessException("不是游戏用户不能购买房卡");
        }

        //订单的信息
        Map<String,Object> order=new HashMap<>();
        //订单详情
        Map<String, Object> orderDetail = new HashMap<>();

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
        order.put("isGameOrder",2);
        order.put("createTime",new Date());
        order.put("orderStatus",1);
        order.put("payIntegral",0);
        order.put("integralAmount",0);
        order.put("giveIntegral",0);
        order.put("proAmount",memberParcelPojo.getParcelPrice());
        order.put("rewardAmount",0);
        order.put("gameId",busiCustomerPojo.getGameId());
        order.put("shopId",memberParcelPojo.getShopId());
        order.put("buyerMessage",oneYuanModel.getBuyerMessage());
        if(CommonUtil.isNotEmpty(busiCustomerRecommendRelationPojo)){
            order.put("promotionUser",busiCustomerRecommendRelationPojo.getRecomGameId());
        }
        order.put("promotionPrice",sysPropertiesUtils.getDecimalValue("memberBackAmount"));

        //订单详情参数
        orderDetail.put("uPrice",0);
        orderDetail.put("prodId",memberParcelPojo.getId());
        orderDetail.put("prodTitle",memberParcelPojo.getParcelTitle());
        orderDetail.put("productName",memberParcelPojo.getParcelName());
        orderDetail.put("photoUrl",memberParcelPojo.getParcelImg());
        orderDetail.put("sellPrice",memberParcelPojo.getParcelPrice());
        orderDetail.put("number",1);
        orderDetail.put("productDistinction",4);
        orderDetail.put("productType",0);
        orderDetail.put("finalPrice",memberParcelPojo.getParcelPrice());
        orderDetail.put("productSpec",memberParcelPojo.getParcelSpeci());
        if(CommonUtil.isNotEmpty(memberParcelPojo.getNumber())){
            orderDetail.put("roomCardPic",memberParcelPojo.getNumber());
        }

        Map<String,Object> parment=new HashMap<>();
        parment.put("order",order);
        parment.put("orderDetail",orderDetail);

        int orderId=orderBusiServiceImpl.oneYuanBuy(parment);
        if(CommonUtil.isEmpty(orderId)){
            throw new BusinessException("购买礼包失败");
        }
        int[] orderIds=new int[]{orderId};
        return orderIds;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OneYuanModel oneYuanModel=(OneYuanModel) model;
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
