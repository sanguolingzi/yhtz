package com.yinhetianze.pojo.customer;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_customer_feedbackImg")
public class BusiCustomerFeedbackImgPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "del_flag")
    private Short delFlag;

    @Column(name = "feedback_id")
    private Integer feedbackId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "sort_no")
    private short sortNo;
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
     * @return feedback_id
     */
    public Integer getFeedbackId() {
        return feedbackId;
    }

    /**
     * @param feedbackId
     */
    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
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
     * @return img_url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public short getSortNo() {
        return sortNo;
    }

    public void setSortNo(short sortNo) {
        this.sortNo = sortNo;
    }
}