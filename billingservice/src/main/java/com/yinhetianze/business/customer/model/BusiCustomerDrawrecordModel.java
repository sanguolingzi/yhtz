package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;
import java.util.Date;

public class BusiCustomerDrawrecordModel extends PageModel {
    private Integer id;

    /**
     * 提现类型 1 余额(奖励金)提现
     */
    private Short drawType;

    /**
     * 提现金额  
     */
    private BigDecimal drawAmount;

    /**
     * 提现手续费
     */
    private BigDecimal serviceCharge;

    /**
     * 最终支付金额
     */
    private BigDecimal finalAmount;

    /**
     * 提现收款账号  bank_card
     */
    private String bankNumber;

    /**
     * 提现申请时间  apply_time
     */
    private String applyTime;

    /**
     * 银行卡提现类型 0 审核通过 1 审核不通过  2待审核
     * 微信提现类型 1 不成功 0成功  2待处理
     */
    private Short auditStatus;

    /**
     * 审核人
     */
    private Integer auditUser;

    /**
     * 审核时间  audit_time
     */
    private Date auditTime;

    /**
     * 收款人  receive_user
     */
    private String receiveUser;

    /**
     * 所属账户
     */
    private Integer bankrollId;

    private Short delFlag;

    /**
     * 提现流水号
     */
    private String drawNumber;


    /**
     * 用户id
     */
    private Integer customerId;

    private String reason;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     *  0 微信提现 1 银行卡提现
     */
    private Short payType;

    /**
     *  1 h5   2 app
     */
    private Short idType;
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
     * 获取提现类型 1 货款提现 
     *
     * @return draw_type - 提现类型 1 货款提现 
     */
    public Short getDrawType() {
        return drawType;
    }

    /**
     * 设置提现类型 1 货款提现 
     *
     * @param drawType 提现类型 1 货款提现 
     */
    public void setDrawType(Short drawType) {
        this.drawType = drawType;
    }

    /**
     * 获取提现金额  
     *
     * @return draw_amount - 提现金额  
     */
    public BigDecimal getDrawAmount() {
        return drawAmount;
    }

    /**
     * 设置提现金额  
     *
     * @param drawAmount 提现金额  
     */
    public void setDrawAmount(BigDecimal drawAmount) {
        this.drawAmount = drawAmount;
    }

    /**
     * 获取提现收款账号  bank_card
     *
     * @return bank_number - 提现收款账号  bank_card
     */
    public String getBankNumber() {
        return bankNumber;
    }

    /**
     * 设置提现收款账号  bank_card
     *
     * @param bankNumber 提现收款账号  bank_card
     */
    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    /**
     * 获取提现申请时间  apply_time
     *
     * @return apply_time - 提现申请时间  apply_time
     */
    public String getApplyTime() {
        return applyTime;
    }

    /**
     * 设置提现申请时间  apply_time
     *
     * @param applyTime 提现申请时间  apply_time
     */
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 获取0 审核通过 1 审核不通过  2待审核
     *
     * @return audit_status - 0 审核通过 1 审核不通过  2待审核
     */
    public Short getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置0 审核通过 1 审核不通过  2待审核
     *
     * @param auditStatus 0 审核通过 1 审核不通过  2待审核
     */
    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取审核人
     *
     * @return audit_user - 审核人
     */
    public Integer getAuditUser() {
        return auditUser;
    }

    /**
     * 设置审核人
     *
     * @param auditUser 审核人
     */
    public void setAuditUser(Integer auditUser) {
        this.auditUser = auditUser;
    }

    /**
     * 获取审核时间  audit_time
     *
     * @return audit_time - 审核时间  audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 设置审核时间  audit_time
     *
     * @param auditTime 审核时间  audit_time
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 获取收款人  receive_user
     *
     * @return receive_user - 收款人  receive_user
     */
    public String getReceiveUser() {
        return receiveUser;
    }

    /**
     * 设置收款人  receive_user
     *
     * @param receiveUser 收款人  receive_user
     */
    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    /**
     * 获取所属账户
     *
     * @return bankroll_id - 所属账户
     */
    public Integer getBankrollId() {
        return bankrollId;
    }

    /**
     * 设置所属账户
     *
     * @param bankrollId 所属账户
     */
    public void setBankrollId(Integer bankrollId) {
        this.bankrollId = bankrollId;
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
     * 获取提现流水号
     *
     * @return draw_number - 提现流水号
     */
    public String getDrawNumber() {
        return drawNumber;
    }

    /**
     * 设置提现流水号
     *
     * @param drawNumber 提现流水号
     */
    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Short getIdType() {
        return idType;
    }

    public void setIdType(Short idType) {
        this.idType = idType;
    }
}