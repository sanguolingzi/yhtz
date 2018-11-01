package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;


public class SmsModel extends PageModel{

    private Integer id;

    /**
     * 短信模板标题
     */
    private String smsTitle;

    /**
     * 短信模板文案
     */
    private String smsContent;

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
     * 获取短信模板标题
     *
     * @return sms_title - 短信模板标题
     */
    public String getSmsTitle() {
        return smsTitle;
    }

    /**
     * 设置短信模板标题
     *
     * @param smsTitle 短信模板标题
     */
    public void setSmsTitle(String smsTitle) {
        this.smsTitle = smsTitle;
    }

    /**
     * 获取短信模板文案
     *
     * @return sms_content - 短信模板文案
     */
    public String getSmsContent() {
        return smsContent;
    }

    /**
     * 设置短信模板文案
     *
     * @param smsContent 短信模板文案
     */
    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }
}