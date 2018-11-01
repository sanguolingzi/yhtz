package com.yinhetianze.business.message.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;

public class BusiMessageDetailPageModel extends PageModel {
    /**
     * 消息类型 0 物流通告 1 通知消息 2 在线客服
     */
    private Short mType;

    private Integer messageId;

    /**
     * 用来处理 已读未读消息业务
     */
    private Date createTime;
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

}