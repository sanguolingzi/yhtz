package com.yinhetianze.business.message.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.Date;

public class BusiMessageDetailModel extends BasicModel{
    private Integer id;

    /**
     * 消息类型 0 物流通告 1 通知消息 2 在线客服
     */
    private Short mType;

    /**
     * 消息标题
     */
    private String mTitle;


    private Integer messageId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 消息内容
     */
    private String mContent;

    /**
     * 图片地址
     */
    private String logoUrl;

    /**
     * 格式化之后的时间
     */
    private String startTime;

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
     * 获取消息类型 0 物流通告 1 通知消息 2 在线客服
     *
     * @return m_type - 消息类型 0 物流通告 1 通知消息 2 在线客服
     */
    public Short getmType() {
        return mType;
    }

    /**
     * 设置消息类型 0 物流通告 1 通知消息 2 在线客服
     *
     * @param mType 消息类型 0 物流通告 1 通知消息 2 在线客服
     */
    public void setmType(Short mType) {
        this.mType = mType;
    }

    /**
     * 获取消息标题
     *
     * @return m_title - 消息标题
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * 设置消息标题
     *
     * @param mTitle 消息标题
     */
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * @return customer_id
     */
    public Integer getMessageId() {
        return messageId;
    }
    /**
     * @param messageId
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
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
     * 获取消息内容
     *
     * @return m_content - 消息内容
     */
    public String getmContent() {
        return mContent;
    }

    /**
     * 设置消息内容
     *
     * @param mContent 消息内容
     */
    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}