package com.yinhetianze.pojo.customer;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_customer_wechat")
public class BusiCustomerWechatPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 微信openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

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
     * 用户code 唯一 防止open_id 暴漏在客户端
     */
    @Column(name = "customer_code")
    private String customerCode;

    /**
     *  0 未注册 1 已注册
     */
    @Column(name = "is_regeister")
    private Short isRegeister;

    /**
     *  1 男  2女  0 未知 微信用户性别编码
     */
    private Short sex;

    /**
     *  微信用户头像
     */
    @Column(name = "headimg_url")
    private String headImgUrl;

    /**
     *  微信用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     *  用于记录解除微信绑定关系后 的Openid 原openid 字段建立了唯一索引
     */
    @Column(name = "open_id_his")
    private String openIdHis;
    /**
     * 记录OpenID 类型 1 h5  2 app
     */
    @Column(name = "id_type")
    private Short idType;

    @Column(name = "id_type_his")
    private Short idTypeHis;
    /**
     * 获取用户
     *
     * @return customer_id - 用户
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置用户
     *
     * @param customerId 用户
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取微信openId
     *
     * @return open_id - 微信openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置微信openId
     *
     * @param openId 微信openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
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
     * 获取用户code 为uuid 唯一 防止open_id 暴漏在客户端
     *
     * @return customer_code - 用户code 为uuid 唯一 防止open_id 暴漏在客户端
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * 设置用户code 为uuid 唯一 防止open_id 暴漏在客户端
     *
     * @param customerCode 用户code 为uuid 唯一 防止open_id 暴漏在客户端
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Short getIsRegeister() {
        return isRegeister;
    }

    public void setIsRegeister(Short isRegeister) {
        this.isRegeister = isRegeister;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenIdHis() {
        return openIdHis;
    }

    public void setOpenIdHis(String openIdHis) {
        this.openIdHis = openIdHis;
    }

    public Short getIdType() {
        return idType;
    }

    public void setIdType(Short idType) {
        this.idType = idType;
    }

    public Short getIdTypeHis() {
        return idTypeHis;
    }

    public void setIdTypeHis(Short idTypeHis) {
        this.idTypeHis = idTypeHis;
    }
}