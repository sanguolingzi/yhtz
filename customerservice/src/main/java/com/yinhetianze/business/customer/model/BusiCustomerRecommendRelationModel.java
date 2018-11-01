package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

/**
 *
 */
public class BusiCustomerRecommendRelationModel extends PageModel{
    private Integer id;
    /**
     * 被推荐人手机号码
     */
    private String phone;
    /**
     * 推荐人
     */
    private Integer recomCustomerId;

    /**
     * 被推荐人
     */
    private Integer recomedCustomerId;

    private String createTime;

    private Short isMember;

    private Integer recomGameId;

    private Integer recomedGameId;

    private Integer showId;

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
     * 获取推荐人
     *
     * @return recom_customer_id - 推荐人
     */
    public Integer getRecomCustomerId() {
        return recomCustomerId;
    }

    /**
     * 设置推荐人
     *
     * @param recomCustomerId 推荐人
     */
    public void setRecomCustomerId(Integer recomCustomerId) {
        this.recomCustomerId = recomCustomerId;
    }

    /**
     * 获取被推荐人
     *
     * @return recomed_customer_id - 被推荐人
     */
    public Integer getRecomedCustomerId() {
        return recomedCustomerId;
    }

    /**
     * 设置被推荐人
     *
     * @param recomedCustomerId 被推荐人
     */
    public void setRecomedCustomerId(Integer recomedCustomerId) {
        this.recomedCustomerId = recomedCustomerId;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Short getIsMember() {
        return isMember;
    }

    public void setIsMember(Short isMember) {
        this.isMember = isMember;
    }

    public Integer getRecomGameId() {
        return recomGameId;
    }

    public void setRecomGameId(Integer recomGameId) {
        this.recomGameId = recomGameId;
    }

    public Integer getRecomedGameId() {
        return recomedGameId;
    }

    public void setRecomedGameId(Integer recomedGameId) {
        this.recomedGameId = recomedGameId;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }
}