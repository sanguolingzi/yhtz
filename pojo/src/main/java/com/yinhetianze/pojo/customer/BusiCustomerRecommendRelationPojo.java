package com.yinhetianze.pojo.customer;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_customer_recommend_relation")
public class BusiCustomerRecommendRelationPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "relation_code")
    private String relationCode;

    @Column(name = "partner_id")
    private Integer partnerId;

    /**
     * 推荐人gameId
     */
    @Column(name = "recom_game_id")
    private Integer recomGameId;

    /**
     * 被推荐人推荐人gameId
     */
    @Column(name = "recomed_game_id")
    private Integer recomedGameId;

    /**
     * 上上级推荐人推荐人gameId 可为空
     */
    @Column(name = "grand_recom_game_id")
    private Integer grandRecomGameId;

    /**
     * 显示Id
     */
    @Column(name = "show_id")
    private Integer showId;

    /**
     * 显示Id
     */
    @Column(name = "p_show_id")
    private Integer pShowId;

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
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
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

    public Integer getGrandRecomGameId() {
        return grandRecomGameId;
    }

    public void setGrandRecomGameId(Integer grandRecomGameId) {
        this.grandRecomGameId = grandRecomGameId;
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