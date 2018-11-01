package com.yinhetianze.pojo.message;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_message")
public class BusiMessagePojo {
    @Id
    private Integer id;

    /**
     * 个人商城活动消息最后阅读时间
     */
    @Column(name = "active_time")
    private Date activeTime;

    /**
     * 个人物流活动消息最后阅读时间
     */
    @Column(name = "logistics_time")
    private Date logisticsTime;

    /**
     * 个人商城公告消息最后阅读时间
     */
    @Column(name = "notice_time")
    private Date noticeTime;

    /**
     * 个人通知消息最后阅读时间
     */
    @Column(name = "info_time")
    private Date infoTime;

    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取个人商城活动消息最后阅读时间
     *
     * @return active_time - 个人商城活动消息最后阅读时间
     */
    public Date getActiveTime() {
        return activeTime;
    }

    /**
     * 设置个人商城活动消息最后阅读时间
     *
     * @param activeTime 个人商城活动消息最后阅读时间
     */
    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    /**
     * 获取个人物流活动消息最后阅读时间
     *
     * @return logistics_time - 个人物流活动消息最后阅读时间
     */
    public Date getLogisticsTime() {
        return logisticsTime;
    }

    /**
     * 设置个人物流活动消息最后阅读时间
     *
     * @param logisticsTime 个人物流活动消息最后阅读时间
     */
    public void setLogisticsTime(Date logisticsTime) {
        this.logisticsTime = logisticsTime;
    }

    /**
     * 获取个人商城公告消息最后阅读时间
     *
     * @return notice_time - 个人商城公告消息最后阅读时间
     */
    public Date getNoticeTime() {
        return noticeTime;
    }

    /**
     * 设置个人商城公告消息最后阅读时间
     *
     * @param noticeTime 个人商城公告消息最后阅读时间
     */
    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    /**
     * 获取个人通知消息最后阅读时间
     *
     * @return info_time - 个人通知消息最后阅读时间
     */
    public Date getInfoTime() {
        return infoTime;
    }

    /**
     * 设置个人通知消息最后阅读时间
     *
     * @param infoTime 个人通知消息最后阅读时间
     */
    public void setInfoTime(Date infoTime) {
        this.infoTime = infoTime;
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