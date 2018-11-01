package com.yinhetianze.pojo.order;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_order_settlement")
public class SettlementPojo {
    /**
     * 店铺申请结算明细Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_id")
    private Integer settlementId;

    /**
     * 店铺Id
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 店铺名称
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 申请人ID
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 订单总金额
     */
    @Column(name = "total_cost")
    private BigDecimal totalCost;

    /**
     * 平台应支付结算金额
     */
    @Column(name = "finalt_total_cost")
    private BigDecimal finaltTotalCost;

    /**
     * 店铺上交平台的佣金
     */
    @Column(name = "rake_cost")
    private BigDecimal rakeCost;

    /**
     * 审核人
     */
    private String reviewer;

    /**
     * 审核时间
     */
    @Column(name = "reviewer_time")
    private Date reviewerTime;

    /**
     * 状态
1.待审核
2.审核通过
3.审核未通过
4.已结算
     */
    private Byte status;

    /**
     * 申请时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 平台给店铺结算后的支付信息,如：对方收款账号，交易号，金额，时间等
     */
    @Column(name = "paymentInfo")
    private String paymentinfo;

    @Column(name = "payment_time")
    private Date paymentTime;

    /**
     * 申请结算的订单id串
     */
    @Column(name = "orders_ids")
    private String ordersIds;

    /**
     * 结算单号
     */
    @Column(name = "settlement_no")
    private String settlementNo;

    /**
     * 审核不通过原因
     */
    @Column(name = "refuse_reason")
    private String refuseReason;

    /**
     *银行卡名称
     */
    @Column(name = "bank_card_name")
    private String bankCardName;

    /**
     * 银行卡号
     */
    @Column(name = "bank_card_no")
    private String bankCardNo;

    /**
     * 持卡人姓名
     */
    @Column(name = "bank_user_name")
    private String bankUserName;

    /**
     * 扣税金额
     */
    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    /**
     * 获取店铺申请结算明细Id
     *
     * @return settlement_id - 店铺申请结算明细Id
     */
    public Integer getSettlementId() {
        return settlementId;
    }

    /**
     * 设置店铺申请结算明细Id
     *
     * @param settlementId 店铺申请结算明细Id
     */
    public void setSettlementId(Integer settlementId) {
        this.settlementId = settlementId;
    }

    /**
     * 获取店铺Id
     *
     * @return shop_id - 店铺Id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺Id
     *
     * @param shopId 店铺Id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取店铺名称
     *
     * @return shop_name - 店铺名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置店铺名称
     *
     * @param shopName 店铺名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 获取申请人ID
     *
     * @return customer_id - 申请人ID
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置申请人ID
     *
     * @param customerId 申请人ID
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取订单总金额
     *
     * @return total_cost - 订单总金额
     */
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    /**
     * 设置订单总金额
     *
     * @param totalCost 订单总金额
     */
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * 获取平台应支付结算金额
     *
     * @return finalt_total_cost - 平台应支付结算金额
     */
    public BigDecimal getFinaltTotalCost() {
        return finaltTotalCost;
    }

    /**
     * 设置平台应支付结算金额
     *
     * @param finaltTotalCost 平台应支付结算金额
     */
    public void setFinaltTotalCost(BigDecimal finaltTotalCost) {
        this.finaltTotalCost = finaltTotalCost;
    }

    /**
     * 获取店铺上交平台的佣金
     *
     * @return rake_cost - 店铺上交平台的佣金
     */
    public BigDecimal getRakeCost() {
        return rakeCost;
    }

    /**
     * 设置店铺上交平台的佣金
     *
     * @param rakeCost 店铺上交平台的佣金
     */
    public void setRakeCost(BigDecimal rakeCost) {
        this.rakeCost = rakeCost;
    }

    /**
     * 获取审核人
     *
     * @return reviewer - 审核人
     */
    public String getReviewer() {
        return reviewer;
    }

    /**
     * 设置审核人
     *
     * @param reviewer 审核人
     */
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * 获取审核时间
     *
     * @return reviewer_time - 审核时间
     */
    public Date getReviewerTime() {
        return reviewerTime;
    }

    /**
     * 设置审核时间
     *
     * @param reviewerTime 审核时间
     */
    public void setReviewerTime(Date reviewerTime) {
        this.reviewerTime = reviewerTime;
    }

    /**
     * 获取状态
1.待审核
2.审核通过
3.审核未通过
4.已结算
     *
     * @return status - 状态
1.待审核
2.审核通过
3.审核未通过
4.已结算
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态
1.待审核
2.审核通过
3.审核未通过
4.已结算
     *
     * @param status 状态
1.待审核
2.审核通过
3.审核未通过
4.已结算
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取申请时间
     *
     * @return create_time - 申请时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置申请时间
     *
     * @param createTime 申请时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取平台给店铺结算后的支付信息,如：对方收款账号，交易号，金额，时间等
     *
     * @return paymentInfo - 平台给店铺结算后的支付信息,如：对方收款账号，交易号，金额，时间等
     */
    public String getPaymentinfo() {
        return paymentinfo;
    }

    /**
     * 设置平台给店铺结算后的支付信息,如：对方收款账号，交易号，金额，时间等
     *
     * @param paymentinfo 平台给店铺结算后的支付信息,如：对方收款账号，交易号，金额，时间等
     */
    public void setPaymentinfo(String paymentinfo) {
        this.paymentinfo = paymentinfo;
    }

    /**
     * @return payment_time
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * @param paymentTime
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getOrdersIds() {
        return ordersIds;
    }

    public void setOrdersIds(String ordersIds) {
        this.ordersIds = ordersIds;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public BigDecimal getTaxAmount(){
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount){
        this.taxAmount = taxAmount;
    }
}