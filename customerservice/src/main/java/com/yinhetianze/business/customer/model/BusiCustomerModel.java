package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.Date;

public class BusiCustomerModel extends BasicModel {

    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 0 女 1男
     */
    private Short sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 头像
     */
    private String photoUrl;

    /**
     * 账号状态 0 未冻结 1已冻结 
     */
    private Short accountStatus;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 登录密码   
     */
    private String loginPassword;

    /**
     * 支付密码  
     */
    private String payPassword;

    /**
     * 注销状态 0 未注销 1 已注销
     */
    private Short cancelStatus;

    /**
     * 个人二维码图片地址
     */
    private String personQrcode;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 是否会员 0 是 1 不是
     */
    private Short isMember;

    /**
     * 是否合作商状态 0 是 1 不是
     */
    private Short isPartner;
    /**
     * 渠道编码
     */
    private String channelCode;
    /**
     *渠道秘钥
     */
    private String channelSecret;
    /**
     * 签名
     */
    private String sign;

    /**
     * 是否校验登陆密码
     */
    private Boolean checkPassword = true;

    private BigDecimal proportion;

    private String recommendCode;
    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取0 女 1男
     *
     * @return sex - 0 女 1男
     */
    public Short getSex() {
        return sex;
    }

    /**
     * 设置0 女 1男
     *
     * @param sex 0 女 1男
     */
    public void setSex(Short sex) {
        this.sex = sex;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取头像
     *
     * @return photo_url - 头像
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 设置头像
     *
     * @param photoUrl 头像
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 获取账号状态 0 未冻结 1已冻结 
     *
     * @return account_status - 账号状态 0 未冻结 1已冻结 
     */
    public Short getAccountStatus() {
        return accountStatus;
    }

    /**
     * 设置账号状态 0 未冻结 1已冻结 
     *
     * @param accountStatus 账号状态 0 未冻结 1已冻结 
     */
    public void setAccountStatus(Short accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * 获取身份证号
     *
     * @return id_card - 身份证号
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证号
     *
     * @param idCard 身份证号
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取登录密码   
     *
     * @return login_password - 登录密码   
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * 设置登录密码   
     *
     * @param loginPassword 登录密码   
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * 获取支付密码  
     *
     * @return pay_password - 支付密码  
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 设置支付密码  
     *
     * @param payPassword 支付密码  
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    /**
     * 获取注销状态 0 未注销 1 已注销
     *
     * @return cancel_status - 注销状态 0 未注销 1 已注销
     */
    public Short getCancelStatus() {
        return cancelStatus;
    }

    /**
     * 设置注销状态 0 未注销 1 已注销
     *
     * @param cancelStatus 注销状态 0 未注销 1 已注销
     */
    public void setCancelStatus(Short cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    /**
     * 获取个人二维码图片地址
     *
     * @return person_qrcode - 个人二维码图片地址
     */
    public String getPersonQrcode() {
        return personQrcode;
    }

    /**
     * 设置个人二维码图片地址
     *
     * @param personQrcode 个人二维码图片地址
     */
    public void setPersonQrcode(String personQrcode) {
        this.personQrcode = personQrcode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Short getIsMember() {
        return isMember;
    }

    public void setIsMember(Short isMember) {
        this.isMember = isMember;
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

    public Boolean getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(Boolean checkPassword) {
        this.checkPassword = checkPassword;
    }

    public Short getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Short isPartner) {
        this.isPartner = isPartner;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }
}