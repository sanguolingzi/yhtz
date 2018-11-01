package com.yinhetianze.pojo.customer;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_customer_drawqueue")
public class BusiCustomerDrawQueuePojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 提现记录id
     */
    @Column(name = "draw_id")
    private Integer drawId;

    /**
     * 提现流水号
     */
    @Column(name = "draw_number")
    private String drawNumber;

    /**
     * 1 h5  2 app
     */
    @Column(name = "id_type")
    private Short idType;

    /**
     * 重试次数 3次以上 不处理
     */
    @Column(name = "retry_count")
    private Short retryCount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 0 未处理 1 已处理
     */
    @Column(name = "is_handle")
    private Short isHandle;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    @Column(name = "error_desc")
    private String errorDesc;

    @Column(name = "error_code")
    private String errorCode;
    /**
     *  0  正常   1 异常
     */
    private Short status;

    @Column(name = "handle_time")
    private Date handleTime;
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
     * 获取提现记录id
     *
     * @return draw_id - 提现记录id
     */
    public Integer getDrawId() {
        return drawId;
    }

    /**
     * 设置提现记录id
     *
     * @param drawId 提现记录id
     */
    public void setDrawId(Integer drawId) {
        this.drawId = drawId;
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

    /**
     * 获取1 h5  2 app
     *
     * @return id_type - 1 h5  2 app
     */
    public Short getIdType() {
        return idType;
    }

    /**
     * 设置1 h5  2 app
     *
     * @param idType 1 h5  2 app
     */
    public void setIdType(Short idType) {
        this.idType = idType;
    }

    /**
     * 获取重试次数 3次以上 不处理
     *
     * @return retry_count - 重试次数 3次以上 不处理
     */
    public Short getRetryCount() {
        return retryCount;
    }

    /**
     * 设置重试次数 3次以上 不处理
     *
     * @param retryCount 重试次数 3次以上 不处理
     */
    public void setRetryCount(Short retryCount) {
        this.retryCount = retryCount;
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
     * 获取0 未处理 1 已处理
     *
     * @return is_handle - 0 未处理 1 已处理
     */
    public Short getIsHandle() {
        return isHandle;
    }

    /**
     * 设置0 未处理 1 已处理
     *
     * @param isHandle 0 未处理 1 已处理
     */
    public void setIsHandle(Short isHandle) {
        this.isHandle = isHandle;
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

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }
}