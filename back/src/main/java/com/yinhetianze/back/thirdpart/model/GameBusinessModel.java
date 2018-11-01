package com.yinhetianze.back.thirdpart.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 游戏业务处理model
 */
public class GameBusinessModel extends BasicModel{
    /**
     * 需要查询的游戏 用户Id
     */
    private Integer gameId;

    /**
     * 渠道编码 (第三方接口签名加密用)
     */
    private String channelCode;
    /**
     *渠道秘钥(第三方接口签名加密用)
     */
    private String channelSecret;
    /**
     * 签名(第三方接口签名加密用)
     */
    private String sign;

    /**
     * 时间戳
     * @return
     */
    private String timestamp;

    /**
     * 订单号
     * @return
     */
    private String tradeNo;

    /**
     * 商品类型
     * @return
     */
    private Integer tradeType;

    /**
     * 订单描述
     * @return
     */
    private String tradeDesc;

    /**
     * 订单金额
     * @return
     */
    private BigDecimal amount;

    /**
     * 商城用户Id
     * @return
     */
    private String  customerId;

    /**
     * 支付时间
     * @return
     */
    private String paymentTime;

    public Integer getGameId(){
        return gameId;
    }

    public void setGameId(Integer gameId){
        this.gameId = gameId;
    }

    public String getChannelCode(){
        return channelCode;
    }

    public void setChannelCode(String channelCode){
        this.channelCode = channelCode;
    }

    public String getChannelSecret(){
        return channelSecret;
    }

    public void setChannelSecret(String channelSecret){
        this.channelSecret = channelSecret;
    }

    public String getSign(){
        return sign;
    }

    public void setSign(String sign){
        this.sign = sign;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

    public String getTradeNo(){
        return tradeNo;
    }

    public void setTradeNo(String tradeNo){
        this.tradeNo = tradeNo;
    }

    public Integer getTradeType(){
        return tradeType;
    }

    public void setTradeType(Integer tradeType){
        this.tradeType = tradeType;
    }

    public String getTradeDesc(){
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc){
        this.tradeDesc = tradeDesc;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public String getCustomerId(){
        return customerId;
    }

    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    public String getPaymentTime(){
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime){
        this.paymentTime = paymentTime;
    }
}