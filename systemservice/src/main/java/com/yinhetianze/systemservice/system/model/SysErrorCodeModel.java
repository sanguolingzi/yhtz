package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;

public class SysErrorCodeModel extends PageModel{
    /**
     * 错误码
     */

    private String errorCode;

    /**
     * 错误消息提示信息
     */

    private String errorMessage;

    /**
     * 信息类型：EXCEPTION, MESSAGE
     */
    private String type;


    private String businessCode;


    private Integer sortNumber;

    private Integer status;


    private Date createTime;

    /**
     * 提示信息备注，描述该条提示作用
     */
    private String remarks;

    /**
     * 获取错误码
     *
     * @return error_code - 错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误码
     *
     * @param errorCode 错误码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取错误消息提示信息
     *
     * @return error_message - 错误消息提示信息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 设置错误消息提示信息
     *
     * @param errorMessage 错误消息提示信息
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 获取信息类型：EXCEPTION, MESSAGE
     *
     * @return type - 信息类型：EXCEPTION, MESSAGE
     */
    public String getType() {
        return type;
    }

    /**
     * 设置信息类型：EXCEPTION, MESSAGE
     *
     * @param type 信息类型：EXCEPTION, MESSAGE
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return business_code
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * @param businessCode
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * @return sort_number
     */
    public Integer getSortNumber() {
        return sortNumber;
    }

    /**
     * @param sortNumber
     */
    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
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

    /**
     * 获取提示信息备注，描述该条提示作用
     *
     * @return remarks - 提示信息备注，描述该条提示作用
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置提示信息备注，描述该条提示作用
     *
     * @param remarks 提示信息备注，描述该条提示作用
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}