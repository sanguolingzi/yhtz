package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

/**
 *   消费者注册  model
 */
public class BusiRegeisterModel extends BasicModel {

    private Integer id;
    /**
     * 联系电话
     */
    private String phone;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     *  推荐人id
     */
    private Integer recommendId;

    /**
     *  sign
     */
    private String secret;

    /**
     * 校验类型  1 手机浏览器  2 微信 3 app 4 pc
     */
    private String clientType = "1";

    /**
     * 微信授权 后台生成code
     */
    private String customerCode;

    /**
     * 会员推荐码
     */
    private String recommendCode;

    /**
     * 临时存储游戏Id
     * @return
     */
    private Integer gameId;

    /**
     * 父级pGameId 可为空
     */
    private Integer pGameId;

    private String gameKey;

    /**
     *  微信绑定类型  1 h5  2 app
     */
    private Short idType;

    /**
     * 是否校验短信验证码  true 校验  false 不校验验证码 但是需要校验redis中是否有 允许用户操作的授权
     */
    private String checkSmsCode = "true" ;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getGameId(){
        return gameId;
    }

    public void setGameId(Integer gameId){
        this.gameId = gameId;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getCheckSmsCode() {
        return checkSmsCode;
    }

    public void setCheckSmsCode(String checkSmsCode) {
        this.checkSmsCode = checkSmsCode;
    }

    public String getGameKey() {
        return gameKey;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getIdType() {
        return idType;
    }

    public void setIdType(Short idType) {
        this.idType = idType;
    }

    public Integer getpGameId() {
        return pGameId;
    }

    public void setpGameId(Integer pGameId) {
        this.pGameId = pGameId;
    }
}