package com.yinhetianze.pojo.order;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_order_rebate")
public class RebatePojo {
    /**
     * 领劵记录id
     */
    @Id
    @Column(name = "record_id")
    private Integer recordId;

    /**
     * 用户ID
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 领劵编号(订单号)
     */
    @Column(name = "record_no")
    private String recordNo;

    /**
     * 领劵金额
     */
    @Column(name = "yoka_amount")
    private BigDecimal yokaAmount;

    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取领劵记录id
     *
     * @return record_id - 领劵记录id
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * 设置领劵记录id
     *
     * @param recordId 领劵记录id
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     * 获取用户ID
     *
     * @return customer_id - 用户ID
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置用户ID
     *
     * @param customerId 用户ID
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取领劵编号(订单号)
     *
     * @return record_no - 领劵编号(订单号)
     */
    public String getRecordNo() {
        return recordNo;
    }

    /**
     * 设置领劵编号(订单号)
     *
     * @param recordNo 领劵编号(订单号)
     */
    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    /**
     * 获取领劵金额
     *
     * @return yoka_amount - 领劵金额
     */
    public BigDecimal getYokaAmount() {
        return yokaAmount;
    }

    /**
     * 设置领劵金额
     *
     * @param yokaAmount 领劵金额
     */
    public void setYokaAmount(BigDecimal yokaAmount) {
        this.yokaAmount = yokaAmount;
    }

    /**
     * 获取订单金额
     *
     * @return order_amount - 订单金额
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置订单金额
     *
     * @param orderAmount 订单金额
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
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
}