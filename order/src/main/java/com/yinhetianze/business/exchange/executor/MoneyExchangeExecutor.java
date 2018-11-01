package com.yinhetianze.business.exchange.executor;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.yinhetianze.business.alipay.Alipay;
import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.business.exchange.service.ExchangeBusiService;
import com.yinhetianze.business.exchange.service.ExchangeInfoService;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.common.business.wx.service.AppWxService;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.ExchangePojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MoneyExchangeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ExchangeBusiService exchangeBusiServiceImpl;

    @Autowired
    private ExchangeInfoService exchangeInfoServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private Alipay alipay;

    @Autowired
    private WxService payService;

    @Autowired
    private AppWxService appPayService;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        ExchangePojo exchangePojo=new ExchangePojo();
        BeanUtils.copyProperties(model, exchangePojo);

        //查询售后记录
        ExchangePojo exchangePojo1=new ExchangePojo();
        exchangePojo1.setId(exchangePojo.getId());
        exchangePojo1=exchangeInfoServiceImpl.findById(exchangePojo1);
        if(CommonUtil.isEmpty(exchangePojo1)){
            throw new BusinessException("传入的ID有误");
        }
        try{
            if(exchangePojo.getMoneyCheck()==2){
                //订单信息
                Map<String,Object> parameter=new HashMap<>();
                parameter.put("ordersNo",exchangePojo1.getOrderNo());
                List<Map<String,Object>> orderList=orderInfoServiceImpl.findBackOrder(parameter);
                if(CommonUtil.isEmpty(orderList)){
                    throw new BusinessException("订单信息异常");
                }
                if(exchangePojo.getRefundTotalAmount().compareTo(exchangePojo1.getApplyAmount())==1){
                    throw new BusinessException("打款金额不能大于申请金额");
                }
                List<Map<String,Object>> orderDetail= orderInfoServiceImpl.findOrderDetail(null, exchangePojo1.getOrderDetailId());
                //优先退消费券
                BigDecimal starAmount=new BigDecimal(orderList.get(0).get("integralAmount")+"");
                /*BigDecimal gameAmount=BigDecimal.ZERO;
                if(CommonUtil.isNotEmpty(orderDetail.get(0).get("gameAmount"))){
                    gameAmount=new BigDecimal(orderDetail.get(0).get("gameAmount")+"").multiply(sysPropertiesUtils.getDecimalValue("uRatio"));
                }*/
                if(exchangePojo.getRefundTotalAmount().compareTo(starAmount)==-1){
                    exchangePojo.setRefundStarCoin(exchangePojo.getRefundTotalAmount());
                    exchangePojo.setRefundAmount(BigDecimal.ZERO);
                }else{
                    exchangePojo.setRefundStarCoin(starAmount);
                    exchangePojo.setRefundAmount(exchangePojo.getRefundTotalAmount().subtract(starAmount));
                }
                //退款到微信支付宝
                if(exchangePojo.getRefundAmount().compareTo(BigDecimal.ZERO)==1){
                    BigDecimal ratio=new BigDecimal("100");
                    if(Integer.valueOf(orderList.get(0).get("payType")+"")==2){         //微信退款
                        WxPayRefundResult refundResult;
                        if(Integer.valueOf(orderList.get(0).get("orderSourse")+"")==3){
                            refundResult=appPayService.refundApi(orderList.get(0).get("totalOrderNo")+"",exchangePojo1.getExchangeNo(),
                                    new BigDecimal(orderList.get(0).get("payAmount")+"").multiply(ratio),exchangePojo.getRefundAmount().multiply(ratio));
                        }else{
                            refundResult=payService.refundApi(orderList.get(0).get("totalOrderNo")+"",exchangePojo1.getExchangeNo(),
                                    new BigDecimal(orderList.get(0).get("payAmount")+"").multiply(ratio),exchangePojo.getRefundAmount().multiply(ratio));
                        }
                        if("SUCCESS".equals(refundResult.getReturnCode())){
                            Map<String,Object> transaction=new HashMap<String,Object>();
                            transaction.put("type", 2);
                            transaction.put("dealNo", refundResult.getRefundId());
                            transaction.put("totalOrdersNo", refundResult.getOutTradeNo());
                            transaction.put("transResult",refundResult.getResultCode());
                            transaction.put("returnMsg", refundResult.getReturnMsg());
                            transaction.put("appid",refundResult.getAppid());
                            transaction.put("amount", refundResult.getRefundFee());
                            transaction.put("tradeTime", new Date());
                            transaction.put("exchangeNo", refundResult.getOutRefundNo());
                            orderBusiServiceImpl.addOrderTransRecord(transaction);
                            //存到数据库
                        }else {
                            throw new BusinessException("微信退款失败");
                        }
                    }else if(Integer.valueOf(orderList.get(0).get("payType")+"")==1){      //支付宝退款
                        AlipayTradeRefundResponse alipayResponse=alipay.refund(exchangePojo.getRefundAmount().toString(),orderList.get(0).get("totalOrderNo")+"",exchangePojo1.getExchangeNo(),response);
                        if("10000".equals(alipayResponse.getCode())&&"Y".equals(alipayResponse.getFundChange())){
                            Map<String,Object> transaction=new HashMap<String,Object>();
                            transaction.put("type", 1);
                            transaction.put("dealNo",alipayResponse.getTradeNo());
                            transaction.put("totalOrdersNo",alipayResponse.getOutTradeNo());
                            transaction.put("transResult",alipayResponse.getMsg());
                            //transaction.put("appid",refundResult.getAppid());
                            transaction.put("amount", new BigDecimal(alipayResponse.getRefundFee()).multiply(new BigDecimal("100")));
                            transaction.put("tradeTime", alipayResponse.getGmtRefundPay());
                            transaction.put("exchangeNo",exchangePojo1.getExchangeNo());
                            orderBusiServiceImpl.addOrderTransRecord(transaction);
                        }else{
                            throw new BusinessException("支付宝退款失败");
                        }
                    }
                }
            }

            exchangePojo.setCompleteTime(new Date());
            exchangePojo.setExchangeStatus((short)5);
            exchangePojo.setOrderDetailId(exchangePojo1.getOrderDetailId());
            int i=exchangeBusiServiceImpl.moneyExchange(exchangePojo,tokenUser.getId());
            if(i!=1){
                throw new BusinessException("卖家打款失败");
            }
            return "success";
        }catch (Exception e){
            throw new BusinessException("退货打款审核失败");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        ExchangeModel exchangeModel=(ExchangeModel) model;
        if(CommonUtil.isEmpty(exchangeModel.getId())){
            errorMessage.rejectNull("id",null,"退款记录ID");
        }
        if(CommonUtil.isEmpty(exchangeModel.getMoneyCheck())){
            errorMessage.rejectNull("moneyCheck",null,"打款审核");
        }
        if(exchangeModel.getMoneyCheck()==2&&CommonUtil.isEmpty(exchangeModel.getRefundTotalAmount())){
            errorMessage.rejectNull("refundTotalAmount",null,"同意打款时，打款金额");
        }
        if(exchangeModel.getMoneyCheck()==3&&CommonUtil.isEmpty(exchangeModel.getRefuseReason())){
            errorMessage.rejectNull("refuseReason",null,"不同意打款时原因");
        }
        return errorMessage;
    }
}
