package com.yinhetianze.pojo.message;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_message_detail")
public class BusiMessageDetailPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 消息类型 0 物流通告 1 通知消息 2 在线客服 3 商城公告 4商城活动
     */
    @Column(name = "m_type")
    private Short mType;

    /**
     * 消息标题
     */
    @Column(name = "m_title")
    private String mTitle;

    @Column(name = "del_flag")
    private Short delFlag;

    @Column(name = "message_id")
    private Integer messageId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 消息内容
     */
    @Column(name = "m_content")
    private String mContent;

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
}