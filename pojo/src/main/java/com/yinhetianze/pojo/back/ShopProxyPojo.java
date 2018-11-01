package com.yinhetianze.pojo.back;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_shop_proxy")
public class ShopProxyPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商家账号
     */
    @Column(name = "shop_account")
    private String shopAccount;

    /**
     * 商家密码
     */
    @Column(name = "shop_password")
    private String shopPassword;

    /**
     * 商家姓名
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 商家名称
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 商家LOGO
     */
    @Column(name = "shop_logo")
    private String shopLogo;

    /**
     * 联系电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 商家地省级ID
     */
    @Column(name = "shop_province")
    private String shopProvince;
    /**
     * 商家地市级ID
     */
    @Column(name = "shop_city")
    private String shopCity;
    /**
     * 商家地区级ID
     */
    @Column(name = "shop_area")
    private String shopArea;

    /**
     * 详细地址
     */
    @Column(name = "shop_address")
    private String shopAddress;

    /**
     * 邮编
     */
    @Column(name = "shop_code")
    private String shopCode;

    /**
     * 商家电话
     */
    @Column(name = "shop_phone")
    private String shopPhone;

    /**
     * 联系邮箱
     */
    @Column(name = "shop_email")
    private String shopEmail;

    /**
     * 结算方式 0 银行卡
     */
    @Column(name = "settlement_method")
    private Short settlementMethod;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 银行卡号
     */
    @Column(name = "bank_card_number")
    private String bankCardNumber;

    /**
     * 关联的用户ID
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 关联的管理员ID
     */
    @Column(name = "optor_id")
    private Integer optorId;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0=有效 1=失效
     */
    @Column(name = "is_valid")
    private Short isValid;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 是否发送短信
     */
    @Column(name = "is_message")
    private  Short isMessage;

    /**
     * 商家openId
     */
    @Column(name = "open_id")
    private  String openId;

    public Short getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(Short isMessage) {
        this.isMessage = isMessage;
    }

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
     * 获取商家账号
     *
     * @return shop_account - 商家账号
     */
    public String getShopAccount() {
        return shopAccount;
    }

    /**
     * 设置商家账号
     *
     * @param shopAccount 商家账号
     */
    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount;
    }

    /**
     * 获取商家密码
     *
     * @return shop_password - 商家密码
     */
    public String getShopPassword() {
        return shopPassword;
    }

    /**
     * 设置商家密码
     *
     * @param shopPassword 商家密码
     */
    public void setShopPassword(String shopPassword) {
        this.shopPassword = shopPassword;
    }

    /**
     * 获取商家姓名
     *
     * @return account_name - 商家姓名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置商家姓名
     *
     * @param accountName 商家姓名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取商家名称
     *
     * @return shop_name - 商家名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置商家名称
     *
     * @param shopName 商家名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    /**
     * 获取联系电话
     *
     * @return contact_phone - 联系电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设置联系电话
     *
     * @param contactPhone 联系电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getShopProvince() {
        return shopProvince;
    }

    public void setShopProvince(String shopProvince) {
        this.shopProvince = shopProvince;
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }

    /**
     * 获取详细地址
     *
     * @return shop_address - 详细地址
     */
    public String getShopAddress() {
        return shopAddress;
    }

    /**
     * 设置详细地址
     *
     * @param shopAddress 详细地址
     */
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    /**
     * 获取邮编
     *
     * @return shop_code - 邮编
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * 设置邮编
     *
     * @param shopCode 邮编
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * 获取商家电话
     *
     * @return shop_phone - 商家电话
     */
    public String getShopPhone() {
        return shopPhone;
    }

    /**
     * 设置商家电话
     *
     * @param shopPhone 商家电话
     */
    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    /**
     * 获取联系邮箱
     *
     * @return shop_email - 联系邮箱
     */
    public String getShopEmail() {
        return shopEmail;
    }

    /**
     * 设置联系邮箱
     *
     * @param shopEmail 联系邮箱
     */
    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    /**
     * 获取结算方式 0 银行卡
     *
     * @return settlement_method - 结算方式 0 银行卡
     */
    public Short getSettlementMethod() {
        return settlementMethod;
    }

    /**
     * 设置结算方式 0 银行卡
     *
     * @param settlementMethod 结算方式 0 银行卡
     */
    public void setSettlementMethod(Short settlementMethod) {
        this.settlementMethod = settlementMethod;
    }

    /**
     * 获取开户银行
     *
     * @return bank - 开户银行
     */
    public String getBank() {
        return bank;
    }

    /**
     * 设置开户银行
     *
     * @param bank 开户银行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取银行卡号
     *
     * @return bank_card_number - 银行卡号
     */
    public String getBankCardNumber() {
        return bankCardNumber;
    }

    /**
     * 设置银行卡号
     *
     * @param bankCardNumber 银行卡号
     */
    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    /**
     * 获取关联的用户ID
     *
     * @return customer_id - 关联的用户ID
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置关联的用户ID
     *
     * @param customerId 关联的用户ID
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取关联的管理员ID
     *
     * @return optor_id - 关联的管理员ID
     */
    public Integer getOptorId() {
        return optorId;
    }

    /**
     * 设置关联的管理员ID
     *
     * @param optorId 关联的管理员ID
     */
    public void setOptorId(Integer optorId) {
        this.optorId = optorId;
    }

    /**
     * @return create_user
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * @return update_user
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取0=有效 1=失效
     *
     * @return is_valid - 0=有效 1=失效
     */
    public Short getIsValid() {
        return isValid;
    }

    /**
     * 设置0=有效 1=失效
     *
     * @param isValid 0=有效 1=失效
     */
    public void setIsValid(Short isValid) {
        this.isValid = isValid;
    }

    /**
     * 获取0 正常 1 已删除
     *
     * @return del_flag - 0 正常 1 已删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置0 正常 1 已删除
     *
     * @param delFlag 0 正常 1 已删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}