package com.yinhetianze.pojo.product;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_fresher_reward")
public class ProductFresherRewardPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * gameId
     */
    @Column(name = "game_id")
    private Integer gameId;

    /**
     * 是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     */
    @Column(name = "STATUS")
    private Short status;

    /**
     * 兑换的商品ID
     */
    @Column(name = "prod_id")
    private Integer prodId;

    /**
     * U币抵扣量
     */
    @Column(name = "exchange_u_price")
    private BigDecimal exchangeUPrice;

    /**
     * 兑换时间
     */
    @Column(name = "exchange_time")
    private Date exchangeTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "regeister_time")
    private Date regeisterTime;


    /**
     * 定时任务处理标识 0 已处理 1 未处理
     */
    @Column(name = "handle_status")
    private Short handleStatus;

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
     * 获取是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     *
     * @return STATUS - 是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     *
     * @param status 是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 获取兑换的商品ID
     *
     * @return prod_id - 兑换的商品ID
     */
    public Integer getProdId() {
        return prodId;
    }

    /**
     * 设置兑换的商品ID
     *
     * @param prodId 兑换的商品ID
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    /**
     * 获取U币抵扣量
     *
     * @return exchange_u_price - U币抵扣量
     */
    public BigDecimal getExchangeUPrice() {
        return exchangeUPrice;
    }

    /**
     * 设置U币抵扣量
     *
     * @param exchangeUPrice U币抵扣量
     */
    public void setExchangeUPrice(BigDecimal exchangeUPrice) {
        this.exchangeUPrice = exchangeUPrice;
    }

    /**
     * 获取兑换时间
     *
     * @return exchange_time - 兑换时间
     */
    public Date getExchangeTime() {
        return exchangeTime;
    }

    /**
     * 设置兑换时间
     *
     * @param exchangeTime 兑换时间
     */
    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Date getRegeisterTime() {
        return regeisterTime;
    }

    public void setRegeisterTime(Date regeisterTime) {
        this.regeisterTime = regeisterTime;
    }

    public Short getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Short handleStatus) {
        this.handleStatus = handleStatus;
    }
}