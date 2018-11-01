package com.yinhetianze.business.exchange.executor;

import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.business.exchange.service.ExchangeBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.ExchangePojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddExchangeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ExchangeBusiService exchangeBusiServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ExchangeModel exchangeModel=(ExchangeModel) model;
        ExchangePojo exchangePojo=new ExchangePojo();
        Map<String,Object> parameter=new HashMap<>();
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //查询订单信息
        parameter.put("ordersNo",exchangeModel.getOrderNo());
        parameter.put("customerId",tokenUser.getId());
        List<Map<String,Object>> orderList=orderInfoServiceImpl.findBackOrder(parameter);
        if(CommonUtil.isEmpty(orderList)){
            throw new BusinessException("传入的订单号有误");
        }
        //查询店铺负责人，就是商家
        //店铺信息
        BusiShopPojo busiShopPojo = new BusiShopPojo();
        busiShopPojo.setId(Integer.valueOf(orderList.get(0).get("shopId")+""));
        busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
        if(CommonUtil.isEmpty(busiShopPojo)){
            throw new BusinessException("没有相关店铺信息");
        }


        //售后单信息
        BeanUtils.copyProperties(model, exchangePojo);
        exchangePojo.setExchangeNo(CommonUtil.getSerialnumber());
        exchangePojo.setUserId(busiShopPojo.getCustomerId());
        exchangePojo.setCustomerId(tokenUser.getId());
        exchangePojo.setShopId(busiShopPojo.getId());
        exchangePojo.setProxyShopId(Integer.valueOf(orderList.get(0).get("proxyShopId")+""));
        exchangePojo.setOrderAmount(new BigDecimal(orderList.get(0).get("totalAmount")+""));

        if(Integer.valueOf(orderList.get(0).get("orderStatus")+"")<4){
            throw new BusinessException("此状态下不能退货");
        }
        Map<String,Object> orderDetail=orderInfoServiceImpl.findOrderDetail(null,exchangeModel.getOrderDetailId()).get(0);
        if(CommonUtil.isEmpty(orderDetail)){
            throw new BusinessException("传入的订单详情ID有误");
        }
        BigDecimal totalExchangeAmount=new BigDecimal(orderDetail.get("number")+"").multiply(new BigDecimal(orderDetail.get("sellPrice")+""));
        if(exchangeModel.getApplyAmount().compareTo(totalExchangeAmount)==1) {
            throw new BusinessException("退货退款时的金额不能大于所退商品总金额");
        }
        if(CommonUtil.isNotEmpty(orderDetail.get("gameAmount"))){
            exchangePojo.setApplyAmount(exchangeModel.getApplyAmount().subtract(new BigDecimal(orderDetail.get("gameAmount")+"").multiply(sysPropertiesUtils.getDecimalValue("uRatio"))));
        }else {
            exchangePojo.setApplyAmount(exchangeModel.getApplyAmount());
        }


        int i=exchangeBusiServiceImpl.addExchange(exchangePojo);
        if(i!=1){
            throw new BusinessException("添加售后单失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        ExchangeModel exchangeModel=(ExchangeModel) model;
        if(CommonUtil.isEmpty(exchangeModel.getOrderNo())){
            errorMessage.rejectNull("orderNo",null,"订单号");
        }
        if(CommonUtil.isEmpty(exchangeModel.getExchangeReason())){
            errorMessage.rejectNull("exchangeReason",null,"售后原因");
        }
        if(CommonUtil.isEmpty(exchangeModel.getApplyAmount())){
            errorMessage.rejectNull("applyAmount",null,"退款金额");
        }
        if(CommonUtil.isEmpty(exchangeModel.getOrderDetailId())){
            errorMessage.rejectNull("orderDetailId",null,"退换货时订单详情ID");
        }
        return errorMessage;
    }
}
