package com.yinhetianze.common.business.wx.service;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.yinhetianze.core.utils.CommonUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AppWxService {
    @Autowired
    protected WxMpService appWxMpService;
    @Autowired
    private WxPayService appPayService;
    @Autowired
    private WxPayConfig appPayConfig;


    /**
     * 获取授权
     *
     * @param code 微信返回的code
     * @return 返回对象里面拥有 openid   token  unionId
     * @throws WxErrorException
     */
    public WxMpOAuth2AccessToken getOAuth2AccessToken(String code) throws WxErrorException {
        return this.appWxMpService.oauth2getAccessToken(code);
    }

    /**
     * 获取微信用户信息  通过openId 获取用户信息
     *
     * @return
     * @throws WxErrorException
     */
    public WxMpUser getWxUserInfoByOpenId(String openId) throws WxErrorException {
        return appWxMpService.getUserService().userInfo(openId, "zh_CN");
    }

    /**
     * 通过code获取微信用户信息
     *
     * @param code
     * @return
     * @throws WxErrorException
     */
    public WxMpUser getWxUserInfoByCode(String code) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = getOAuth2AccessToken(code);
        if (CommonUtil.isNotEmpty(wxMpOAuth2AccessToken)) {
            return getWxUserInfoByOpenId(wxMpOAuth2AccessToken.getOpenId());
        }
        return null;
    }

    /**
     * 返回前端js 调起微信支付的参数
     *
     * @param openid    用户opendi
     * @param orderNo   商城订单号  32位以内
     * @param totalFee  订单金额   单位 分
     * @param tradeType 支付来源  JSAPI，NATIVE，APP
     * @param body      商品描叙
     * @param createIp  用户下单ip
     * @param notifyURL 回调地址
     * @return
     */
    public Map<String, String> getJSSDKPayInfo(String openid, String orderNo,
                                               String totalFee, String tradeType,
                                               String body, String createIp,
                                               String notifyURL) throws WxPayException {
        WxPayUnifiedOrderRequest prepayInfo = getOrderInfo(openid, orderNo, totalFee, tradeType, body, createIp, notifyURL);
        Map<String, String> payInfo = appPayService.getPayInfo(prepayInfo);
        return payInfo;
    }

    /**
     * 返回预支付结果，一般不需要调用此方法
     *
     * @param openid    用户opendi
     * @param orderNo   商城订单号  32位以内
     * @param totalFee  订单金额   单位 分
     * @param tradeType 支付来源  JSAPI，NATIVE，APP
     * @param body      商品描叙
     * @param createIp  用户下单ip
     * @param notifyURL 回调地址
     * @return
     */
    public WxPayUnifiedOrderResult getPrepayId(String openid, String orderNo,
                                               String totalFee, String tradeType,
                                               String body, String createIp,
                                               String notifyURL) throws WxPayException {
        WxPayUnifiedOrderRequest prepayInfo = getOrderInfo(openid, orderNo, totalFee, tradeType, body, createIp, notifyURL);
        WxPayUnifiedOrderResult result = this.appPayService.unifiedOrder(prepayInfo);
        return result;
    }

    /**
     * @param openid    用户opendi
     * @param orderNo   商城订单号  32位以内
     * @param totalFee  订单金额   单位 分
     * @param tradeType 支付来源  JSAPI，NATIVE，APP
     * @param body      商品描叙
     * @param createIp  用户下单ip
     * @param notifyURL 回调地址
     * @return
     */
    private WxPayUnifiedOrderRequest getOrderInfo(String openid, String orderNo,
                                                  String totalFee, String tradeType,
                                                  String body, String createIp,
                                                  String notifyURL) {
        WxPayUnifiedOrderRequest prepayInfo = WxPayUnifiedOrderRequest.newBuilder()
                .openid(openid)
                .outTradeNo(orderNo)
                .totalFee(Integer.valueOf(totalFee))
                .body(body)
                .tradeType(tradeType)
                .spbillCreateIp(createIp)
                .notifyURL(notifyURL)//TODO(user) 填写通知回调地址
                .build();
        return prepayInfo;
    }

    public WxPayRefundResult refundApi(String totalOrdersNo,String exchangeNo,BigDecimal totalAmount,BigDecimal refundAmount) throws WxPayException {
        WxPayRefundRequest wxPayRefundResult = new WxPayRefundRequest();
        wxPayRefundResult.setAppid(this.appPayConfig.getAppId());
        wxPayRefundResult.setMchId(this.appPayConfig.getMchId());
        wxPayRefundResult.setNonceStr(String.valueOf(System.currentTimeMillis()));
        wxPayRefundResult.setOutTradeNo(totalOrdersNo);
        wxPayRefundResult.setOutRefundNo(exchangeNo);
        wxPayRefundResult.setTotalFee(totalAmount.intValue());
        wxPayRefundResult.setRefundFee(refundAmount.intValue());
        WxPayRefundResult result = this.appPayService.refund(wxPayRefundResult);
        return result;
    }

    public WxPayRefundQueryResult refundList(){
        WxPayRefundQueryResult result = null;
        try {
            result = this.appPayService.refundQuery(null,"20180119085939698195",null, null);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return result;
    }

    //查询微信支付的订单信息，transactionId为微信订单号，outTradeNo为商户订单号，两者2选1
    public WxPayOrderQueryResult getWxPayInfo(String transactionId,String outTradeNo) throws WxPayException{
        return appPayService.queryOrder(transactionId,outTradeNo);
    }

    public Map<String,String> getPayInfo(WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest)throws WxPayException{
        return appPayService.getPayInfo(wxPayUnifiedOrderRequest);
    }


    public WxMpUser getAppWxMpUser(String code) throws Exception{
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = appWxMpService.oauth2getAccessToken(code);
        WxMpUser wxMpUser = appWxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,null);
        return wxMpUser;
    }
}
