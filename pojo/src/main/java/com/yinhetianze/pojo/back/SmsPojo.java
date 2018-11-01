package com.yinhetianze.pojo.back;

import javax.persistence.*;

@Table(name = "busi_sys_sms")
public class SmsPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 短信模板标题
     */
    @Column(name = "sms_title")
    private String smsTitle;

    /**
     * 短信模板文案
     */
    @Column(name = "sms_content")
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