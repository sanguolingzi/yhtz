package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.Date;

public class BusiCustomerFeedbackModel extends BasicModel {
    private Integer id;

    /**
     * 0 功能异常 1体验问题 2新功能建议 3其它
     */
    private Short cType;

    /**
     * 反馈备注
     */
    private String remark;

    private Integer customerId;

    private String phone;

    private String nickName;

    private String fEmail;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 反馈处理状态 
            0 未处理  1已处理  
     */
    private Short fStatus;
    /**
     * 反馈内容
     */
    private String fDescription;

    /**
     * 图片地址集合
     */
    private String imgUrls;
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
     * 获取0 功能异常 1体验问题 2新功能建议 3其它
     *
     * @return c_type - 0 功能异常 1体验问题 2新功能建议 3其它
     */
    public Short getcType() {
        return cType;
    }

    /**
     * 设置0 功能异常 1体验问题 2新功能建议 3其它
     *
     * @param cType 0 功能异常 1体验问题 2新功能建议 3其它
     */
    public void setcType(Short cType) {
        this.cType = cType;
    }

    /**
     * 获取反馈备注
     *
     */
    public String getRemark() {
        return remark;
    }
    /**
     * 设置反馈备注
     *
     * @param remark 反馈备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return customer_id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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
     * 获取反馈处理状态 
            0 未处理  1已处理  
     *
     * @return f_status - 反馈处理状态 
            0 未处理  1已处理  
     */
    public Short getfStatus() {
        return fStatus;
    }

    /**
     * 设置反馈处理状态 
            0 未处理  1已处理  
     *
     * @param fStatus 反馈处理状态 
            0 未处理  1已处理  
     */
    public void setfStatus(Short fStatus) {
        this.fStatus = fStatus;
    }

    /**
     * @return f_description
     */
    public String getfDescription() {
        return fDescription;
    }

    /**
     * @param fDescription
     */
    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getfEmail() {
        return fEmail;
    }

    public void setfEmail(String fEmail) {
        this.fEmail = fEmail;
    }
}