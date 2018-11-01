package com.yinhetianze.cachedata.errorcode;

import java.io.Serializable;
import java.util.Date;

/**
 * 错误编码/异常编码值对象
 */
public class ErrorCodeInfo implements Serializable
{
    private String errorCode;

    private String errorMessage;

    private String type;

    private String businessCode;

    private Integer sortNumber;

    private Integer status;

    private Date createTime;

    private String remarks;

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getBusinessCode()
    {
        return businessCode;
    }

    public void setBusinessCode(String businessCode)
    {
        this.businessCode = businessCode;
    }

    public Integer getSortNumber()
    {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber)
    {
        this.sortNumber = sortNumber;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
}
