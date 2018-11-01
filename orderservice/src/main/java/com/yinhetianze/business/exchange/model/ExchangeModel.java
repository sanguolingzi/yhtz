package com.yinhetianze.business.exchange.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;
import java.util.Date;

public class ExchangeModel extends PageModel{

    private Integer id;

    /**
     * 售后单号
     */
    private String exchangeNo;

    /**
     * 订单详情Id
     */
    private Integer orderDetailId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 店铺ID
     */
    private Integer shopId;

    /**
     * 售后类型：1.退货退款 2.换货 3.退款
     */
    private Short exchangeType;

    /**
     * 售后原因（1质量问题 2效果不符 3少件 4商品破损 5假冒品牌 6发票问题 7其他）
     */
    private Short exchangeReason;

    /**
     * 退款说明
     */
    private String exchangeDesc;

    /**
     * 退货图片
     */
    private String exchangeImg;

    /**
     * 退货人
     */
    private Integer customerId;

    /**
     * 退货状态（1.待处理 2.待发货 3.已发货 4.已收货 5打款 6完成 7卖家拒绝打款）
     */
    private Short exchangeStatus;

    /**
     * 申请退款金额
     */
    private BigDecimal applyAmount;
    /**
     * 退的余额
     */
    private BigDecimal refundAmount;

    /**
     * 退的星币
     */
    private BigDecimal refundStarCoin;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 实际退款金额
     */
    private BigDecimal refundTotalAmount;

    /**
     * 快递公司
     */
    private String companyName;

    /**
     * 快递单号
     */
    private String expressCode;

    /**
     * 快递运费
     */
    private BigDecimal expressFreight;

    /**
     * 售后单审核状态（1通过 2拒绝 3待审核）
     */
    private Short checkState;

    /**
     * 打款审核状态（1通过 2拒绝 3待审核）
     */
    private Short moneyCheck;

    /**
     * 拒绝理由
     */
    private String refuseReason;

    /**
     * 操作人ID
     */
    private Integer userId;

    /**
     * 退货时间
     */
    private Date createTime;

    /**
     * 卖家反馈时间
     */
    private Date updateTime;

    /**
     * 买家发快递时间
     */
    private Date expressTime;

    /**
     * 卖家收货时间
     */
    private Date collectTime;

    /**
     * 完成时间
     */
    private Date completeTime;

    //时间排序参数(1降 0升)
    private Integer createTimeSort;

    //申请售后金额排序参数(1降 0升)
    private Integer applyAmountSort;

    private String details;

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
     * 获取售后单号
     *
     * @return exchange_no - 售后单号
     */
    public String getExchangeNo() {
        return exchangeNo;
    }

    /**
     * 设置售后单号
     *
     * @param exchangeNo 售后单号
     */
    public void setExchangeNo(String exchangeNo) {
        this.exchangeNo = exchangeNo;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取售后类型：1.退货退款 2.换货 3.退款
     *
     * @return exchange_type - 售后类型：1.退货退款 2.换货 3.退款
     */
    public Short getExchangeType() {
        return exchangeType;
    }

    /**
     * 设置售后类型：1.退货退款 2.换货 3.退款
     *
     * @param exchangeType 售后类型：1.退货退款 2.换货 3.退款
     */
    public void setExchangeType(Short exchangeType) {
        this.exchangeType = exchangeType;
    }

    /**
     * 获取售后原因（1质量问题 2效果不符 3少件 4商品破损 5假冒品牌 6发票问题 7其他）
     *
     * @return exchange_reason - 售后原因（1质量问题 2效果不符 3少件 4商品破损 5假冒品牌 6发票问题 7其他）
     */
    public Short getExchangeReason() {
        return exchangeReason;
    }

    /**
     * 设置售后原因（1质量问题 2效果不符 3少件 4商品破损 5假冒品牌 6发票问题 7其他）
     *
     * @param exchangeReason 售后原因（1质量问题 2效果不符 3少件 4商品破损 5假冒品牌 6发票问题 7其他）
     */
    public void setExchangeReason(Short exchangeReason) {
        this.exchangeReason = exchangeReason;
    }

    /**
     * 获取退款说明
     *
     * @return exchange_desc - 退款说明
     */
    public String getExchangeDesc() {
        return exchangeDesc;
    }

    /**
     * 设置退款说明
     *
     * @param exchangeDesc 退款说明
     */
    public void setExchangeDesc(String exchangeDesc) {
        this.exchangeDesc = exchangeDesc;
    }

    /**
     * 获取退货图片
     *
     * @return exchange_img - 退货图片
     */
    public String getExchangeImg() {
        return exchangeImg;
    }

    /**
     * 设置退货图片
     *
     * @param exchangeImg 退货图片
     */
    public void setExchangeImg(String exchangeImg) {
        this.exchangeImg = exchangeImg;
    }

    /**
     * 获取退货人
     *
     * @return customer_id - 退货人
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置退货人
     *
     * @param customerId 退货人
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取退货状态（0.待处理 1.等待退货 2.等待收货 3.已退款 4.售后完成）
     *
     * @return exchange_status - 退货状态（0.待处理 1.等待退货 2.等待收货 3.已退款 4.售后完成）
     */
    public Short getExchangeStatus() {
        return exchangeStatus;
    }

    /**
     * 设置退货状态（0.待处理 1.等待退货 2.等待收货 3.已退款 4.售后完成）
     *
     * @param exchangeStatus 退货状态（0.待处理 1.等待退货 2.等待收货 3.已退款 4.售后完成）
     */
    public void setExchangeStatus(Short exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    /**
     * 获取退款金额
     *
     * @return refund_amount - 退款金额
     */
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    /**
     * 设置退款金额
     *
     * @param refundAmount 退款金额
     */
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * 获取退的星币
     *
     * @return refund_star_coin - 退的星币
     */
    public BigDecimal getRefundStarCoin() {
        return refundStarCoin;
    }

    /**
     * 设置退的星币
     *
     * @param refundStarCoin 退的星币
     */
    public void setRefundStarCoin(BigDecimal refundStarCoin) {
        this.refundStarCoin = refundStarCoin;
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
     * 获取快递公司
     *
     * @return company_name - 快递公司
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置快递公司
     *
     * @param companyName 快递公司
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取快递单号
     *
     * @return express_code - 快递单号
     */
    public String getExpressCode() {
        return expressCode;
    }

    /**
     * 设置快递单号
     *
     * @param expressCode 快递单号
     */
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    /**
     * 获取快递运费
     *
     * @return express_freight - 快递运费
     */
    public BigDecimal getExpressFreight() {
        return expressFreight;
    }

    /**
     * 设置快递运费
     *
     * @param expressFreight 快递运费
     */
    public void setExpressFreight(BigDecimal expressFreight) {
        this.expressFreight = expressFreight;
    }

    /**
     * 获取审核状态（1通过 2拒绝 3待审核）
     *
     * @return check_state - 审核状态（1通过 2拒绝 3待审核）
     */
    public Short getCheckState() {
        return checkState;
    }

    /**
     * 设置审核状态（1通过 2拒绝 3待审核）
     *
     * @param checkState 审核状态（1通过 2拒绝 3待审核）
     */
    public void setCheckState(Short checkState) {
        this.checkState = checkState;
    }

    /**
     * 获取拒绝理由
     *
     * @return refuse_reason - 拒绝理由
     */
    public String getRefuseReason() {
        return refuseReason;
    }

    /**
     * 设置拒绝理由
     *
     * @param refuseReason 拒绝理由
     */
    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    /**
     * 获取操作人ID
     *
     * @return user_id - 操作人ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置操作人ID
     *
     * @param userId 操作人ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取退货时间
     *
     * @return create_time - 退货时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置退货时间
     *
     * @param createTime 退货时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取卖家反馈时间
     *
     * @return update_time - 卖家反馈时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置卖家反馈时间
     *
     * @param updateTime 卖家反馈时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取买家发快递时间
     *
     * @return express_time - 买家发快递时间
     */
    public Date getExpressTime() {
        return expressTime;
    }

    /**
     * 设置买家发快递时间
     *
     * @param expressTime 买家发快递时间
     */
    public void setExpressTime(Date expressTime) {
        this.expressTime = expressTime;
    }

    /**
     * 获取完成时间
     *
     * @return complete_time - 完成时间
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * 设置完成时间
     *
     * @param completeTime 完成时间
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public BigDecimal getRefundTotalAmount() {
        return refundTotalAmount;
    }

    public void setRefundTotalAmount(BigDecimal refundTotalAmount) {
        this.refundTotalAmount = refundTotalAmount;
    }

    public Short getMoneyCheck() {
        return moneyCheck;
    }

    public void setMoneyCheck(Short moneyCheck) {
        this.moneyCheck = moneyCheck;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCreateTimeSort() {
        return createTimeSort;
    }

    public void setCreateTimeSort(Integer createTimeSort) {
        this.createTimeSort = createTimeSort;
    }

    public Integer getApplyAmountSort() {
        return applyAmountSort;
    }

    public void setApplyAmountSort(Integer applyAmountSort) {
        this.applyAmountSort = applyAmountSort;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}