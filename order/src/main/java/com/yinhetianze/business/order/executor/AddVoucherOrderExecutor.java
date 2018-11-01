package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.business.order.model.OneYuanModel;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.service.MemberParcelInfoService;
import com.yinhetianze.business.voucher.service.info.VoucherInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.pojo.product.MemberParcelPojo;
import com.yinhetianze.pojo.product.VoucherlPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddVoucherOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Autowired
    private MemberParcelInfoService memberParcelInfoServiceImpl;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private VoucherInfoService voucherInfoServiceImpl;

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
        //一个人只能兑换一次
        VoucherlPojo voucherlPojo=new VoucherlPojo();
        voucherlPojo.setCustId(tokenUser.getId());
        voucherlPojo.setId(oneYuanModel.getOutsideId());
        voucherlPojo=voucherInfoServiceImpl.selectOne(voucherlPojo);
        if(CommonUtil.isEmpty(voucherlPojo)||voucherlPojo.getStatus()==1){
            throw new BusinessException("您已经兑换过");
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
        memberParcelPojo.setId(oneYuanModel.getProdId());
        memberParcelPojo=memberParcelInfoServiceImpl.selectOne(memberParcelPojo);
        if(CommonUtil.isEmpty(memberParcelPojo)){
            throw new BusinessException("传入的礼包ID有误");
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
        order.put("freight",0);
        order.put("payAmount",0);
        order.put("payType",6);
        order.put("number",1);
        order.put("totalAmount",0);
        order.put("isGameOrder",6);
        order.put("createTime",new Date());
        order.put("orderStatus",2);
        order.put("payIntegral",0);
        order.put("integralAmount",0);
        order.put("giveIntegral",0);
        order.put("proAmount",memberParcelPojo.getParcelPrice());
        order.put("rewardAmount",0);
        order.put("gameId",busiCustomerPojo.getGameId());
        order.put("shopId",memberParcelPojo.getShopId());
        order.put("buyerMessage",oneYuanModel.getBuyerMessage());

        order.put("voucherId",oneYuanModel.getOutsideId());

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

        Map<String,Object> parment=new HashMap<>();
        parment.put("order",order);
        parment.put("orderDetail",orderDetail);

        int orderId=orderBusiServiceImpl.addVoucherOrder(parment);
        if(CommonUtil.isEmpty(orderId)){
            throw new BusinessException("兑换商品失败");
        }
        int[] orderIds=new int[]{orderId};
        return orderIds;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OneYuanModel oneYuanModel=(OneYuanModel) model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(oneYuanModel.getOutsideId())){
            error.rejectNull("outsideId",null,"外部ID");
        }
        if(CommonUtil.isEmpty(oneYuanModel.getProdId())){
            error.rejectNull("prodId",null,"商品ID");
        }
        if(CommonUtil.isEmpty(oneYuanModel.getReceiveaddressId())){
            error.rejectNull("receiveaddressId",null,"收货地址ID");
        }
        return error;
    }
}
