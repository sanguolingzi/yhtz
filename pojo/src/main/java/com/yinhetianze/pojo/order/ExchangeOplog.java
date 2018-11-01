package com.yinhetianze.pojo.order;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_order_exchange_oplog")
public class ExchangeOplog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private Date operateTime;

    /**
     * 操作前状态
     */
    @Column(name = "once_exchange_order_status")
    private Short onceExchangeOrderStatus;

    /**
     * 操作后状态
     */
    @Column(name = "exchange_order_status")
    private Short exchangeOrderStatus;

    /**
     * 所属售后单id
     */
    @Column(name = "exchange_order_id")
    private Integer exchangeOrderId;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 操作人
     */
    @Column(name = "actor_id")
    private Integer actorId;

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
     * 获取操作时间
     *
     * @return operate_time - 操作时间
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * 设置操作时间
     *
     * @param operateTime 操作时间
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 获取操作前状态
     *
     * @return once_exchange_order_status - 操作前状态
     */
    public Short getOnceExchangeOrderStatus() {
        return onceExchangeOrderStatus;
    }

    /**
     * 设置操作前状态
     *
     * @param onceExchangeOrderStatus 操作前状态
     */
    public void setOnceExchangeOrderStatus(Short onceExchangeOrderStatus) {
        this.onceExchangeOrderStatus = onceExchangeOrderStatus;
    }

    /**
     * 获取操作后状态
     *
     * @return exchange_order_status - 操作后状态
     */
    public Short getExchangeOrderStatus() {
        return exchangeOrderStatus;
    }

    /**
     * 设置操作后状态
     *
     * @param exchangeOrderStatus 操作后状态
     */
    public void setExchangeOrderStatus(Short exchangeOrderStatus) {
        this.exchangeOrderStatus = exchangeOrderStatus;
    }

    /**
     * 获取所属售后单id
     *
     * @return exchange_order_id - 所属售后单id
     */
    public Integer getExchangeOrderId() {
        return exchangeOrderId;
    }

    /**
     * 设置所属售后单id
     *
     * @param exchangeOrderId 所属售后单id
     */
    public void setExchangeOrderId(Integer exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
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
     * 获取操作人
     *
     * @return actor_id - 操作人
     */
    public Integer getActorId() {
        return actorId;
    }

    /**
     * 设置操作人
     *
     * @param actorId 操作人
     */
    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }
}