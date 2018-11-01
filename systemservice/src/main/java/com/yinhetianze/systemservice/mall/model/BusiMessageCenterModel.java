package com.yinhetianze.systemservice.mall.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

/**
 * 个人消息 页面实体
 */
public class BusiMessageCenterModel extends BasicModel {
    private Integer messageId;

    /**
     * 消息类型
     */
    private Short mType;

    /**
     * 消息类型名称
     */
    private String mName;

    /**
     * 未读消息个数
     */
    private Integer messageCount = 0;

    /**
     * 展示的最新消息标题
     */
    private String mTitle;
    /**
     * 展示的最新消息 产生时间
      */
    private String createTime;


    public BusiMessageCenterModel(){
    }


    public BusiMessageCenterModel(Integer messageId, Short mType, String mName){
        this.messageId = messageId;
        this.mName  = mName;
        this.mType = mType;
    }

    /**
     * @return id
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

    public Short getmType() {
        return mType;
    }

    public void setmType(Short mType) {
        this.mType = mType;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}