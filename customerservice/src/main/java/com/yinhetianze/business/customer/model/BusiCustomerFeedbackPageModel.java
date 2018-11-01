package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

public class BusiCustomerFeedbackPageModel extends PageModel {
    private Integer id;

    /**
     * 0 功能异常 1体验问题 2新功能建议 3其它
     */
    private Short cType;
    /**
     * 反馈处理状态 
            0 未处理  1已处理  
     */
    private Short fStatus;


    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

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
     * 获取0 功能异常 1体验问题 2新功能建议 3其它
     *
     * @return c_type - 0 功能异常 1体验问题 2新功能建议 3其它
     */
    public Short getcType() {
        return cType;
    }

    /**
     * 设置0 功能异常 1体验问题 2新功能建议 3其它
     *
     * @param cType 0 功能异常 1体验问题 2新功能建议 3其它
     */
    public void setcType(Short cType) {
        this.cType = cType;
    }


    /**
     * 获取反馈处理状态 
            0 未处理  1已处理  
     *
     * @return f_status - 反馈处理状态 
            0 未处理  1已处理  
     */
    public Short getfStatus() {
        return fStatus;
    }

    /**
     * 设置反馈处理状态 
            0 未处理  1已处理  
     *
     * @param fStatus 反馈处理状态 
            0 未处理  1已处理  
     */
    public void setfStatus(Short fStatus) {
        this.fStatus = fStatus;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}