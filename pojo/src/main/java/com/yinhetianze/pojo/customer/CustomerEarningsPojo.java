package com.yinhetianze.pojo.customer;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_customer_earnings")
public class CustomerEarningsPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 收益所属人
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 收益额度
     */
    private BigDecimal amount;

    /**
     * 收益产生人
     */
    @Column(name = "create_id")
    private Integer createId;

    /**
     * 创建时间 年月日
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取收益所属人
     *
     * @return customer_id - 收益所属人
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置收益所属人
     *
     * @param customerId 收益所属人
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取收益额度
     *
     * @return amount - 收益额度
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置收益额度
     *
     * @param amount 收益额度
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取收益产生人
     *
     * @return create_id - 收益产生人
     */
    public Integer getCreateId() {
        return createId;
    }

    /**
     * 设置收益产生人
     *
     * @param createId 收益产生人
     */
    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    /**
     * 获取创建时间 年月日
     *
     * @return create_time - 创建时间 年月日
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间 年月日
     *
     * @param createTime 创建时间 年月日
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}