package com.yinhetianze.systemservice.thirdpart.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;

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

    /**
     * 登录账号customerPhone
     * @return
     */
    private String customerPhone;

    /**
     * 登录密码 password
     * @return
     */
    private String password;

    /**
     * 推荐人 referrerId
     * @return
     */
    private Integer referrerId;

    /**
     * 奖励游戏币
     * @return
     */
    private BigDecimal gameAmount;

    /**
     * 昵称
     * @return
     */
    private String nickName;

    /**
     * 性别
     * @return
     */
    private Integer sex;

    /**
     * 头像
     * @return
     */
    private String headUrl;

    /**
     * rewardId游戏端奖励编码Id
     * @return
     */
    private Integer rewardId;

    /**
     * 购买房卡数量
     */
    private Integer num;

    /**
     * 推荐人gameId
     * @return
     */
    private Integer pGameId;

    /**
     * 注册时间
     */
    private String registerTime;

    /**
     * 显示Id
     * @return
     */
    private Integer showId;

    /**
     * 推荐显示Id
     * @return
     */
    private Integer pShowId;


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

    public String getCustomerPhone(){
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone){
        this.customerPhone = customerPhone;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Integer getReferrerId(){
        return referrerId;
    }

    public void setReferrerId(Integer referrerId){
        this.referrerId = referrerId;
    }

    public BigDecimal getGameAmount(){
        return gameAmount;
    }

    public void setGameAmount(BigDecimal gameAmount){
        this.gameAmount = gameAmount;
    }

    public String getNickName(){
        return nickName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public Integer getSex(){
        return sex;
    }

    public void setSex(Integer sex){
        this.sex = sex;
    }

    public String getHeadUrl(){
        return headUrl;
    }

    public void setHeadUrl(String headUrl){
        this.headUrl = headUrl;
    }

    public Integer getRewardId(){
        return rewardId;
    }

    public void setRewardId(Integer rewardId){
        this.rewardId = rewardId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    public Integer getpGameId(){
        return pGameId;
    }

    public void setpGameId(Integer pGameId){
        this.pGameId = pGameId;
    }


    public String getRegisterTime(){
        return registerTime;
    }

    public void setRegisterTime(String registerTime){
        this.registerTime = registerTime;
    }

    public Integer getShowId(){
        return showId;
    }

    public void setShowId(Integer showId){
        this.showId = showId;
    }

    public Integer getpShowId(){
        return pShowId;
    }

    public void setpShowId(Integer pShowId){
        this.pShowId = pShowId;
    }
}