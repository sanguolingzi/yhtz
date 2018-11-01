package com.yinhetianze.pojo.customer;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_customer_feedback")
public class BusiCustomerFeedbackPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0 功能异常 1体验问题 2新功能建议 3其它
     */
    @Column(name = "c_type")
    private Short cType;

    /**
     * 反馈备注
     */
    private String remark;

    @Column(name = "del_flag")
    private Short delFlag;

    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 反馈处理状态 
            0 未处理  1已处理  
     */
    @Column(name = "f_status")
    private Short fStatus;

    @Column(name = "f_description")
    private String fDescription;

    @Column(name = "f_email")
    private String fEmail;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return del_flag
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
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

    public String getfEmail() {
        return fEmail;
    }

    public void setfEmail(String fEmail) {
        this.fEmail = fEmail;
    }
}