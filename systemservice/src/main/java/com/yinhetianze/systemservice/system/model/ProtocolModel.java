package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import javax.persistence.*;
import java.util.Date;


public class ProtocolModel  extends PageModel{

    private Integer id;

    /**
     * 协议标题
     */

    private String protocolTitle;

    /**
     * 协议内容
     */

    private String protocolContent;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 协议类型
     */
    private Short type;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 是否显示（0隐藏，1显示）
     */

    private Short isShow;

    /**
     * 创建人
     */

    private Short userId;

    /**
     * 协议ID数组
     */
    private Integer[] protocolIds;

    /**
     * 是否删除
     */
    private short delFlag;


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
     * 获取协议标题
     *
     * @return protocol_title - 协议标题
     */
    public String getProtocolTitle() {
        return protocolTitle;
    }

    /**
     * 设置协议标题
     *
     * @param protocolTitle 协议标题
     */
    public void setProtocolTitle(String protocolTitle) {
        this.protocolTitle = protocolTitle;
    }

    /**
     * 获取协议内容
     *
     * @return protocol_content - 协议内容
     */
    public String getProtocolContent() {
        return protocolContent;
    }

    /**
     * 设置协议内容
     *
     * @param protocolContent 协议内容
     */
    public void setProtocolContent(String protocolContent) {
        this.protocolContent = protocolContent;
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
     * 获取协议类型
     *
     * @return type - 协议类型
     */
    public Short getType() {
        return type;
    }

    /**
     * 设置协议类型
     *
     * @param type 协议类型
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取是否显示（0隐藏，1显示）
     *
     * @return is_show - 是否显示（0隐藏，1显示）
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示（0隐藏，1显示）
     *
     * @param isShow 是否显示（0隐藏，1显示）
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取创建人
     *
     * @return user_id - 创建人
     */
    public Short getUserId() {
        return userId;
    }

    /**
     * 设置创建人
     *
     * @param userId 创建人
     */
    public void setUserId(Short userId) {
        this.userId = userId;
    }

    public Integer[] getProtocolIds() {
        return protocolIds;
    }

    public void setProtocolIds(Integer[] protocolIds) {
        this.protocolIds = protocolIds;
    }

    public short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(short delFlag) {
        this.delFlag = delFlag;
    }
}