package com.yinhetianze.pojo.back;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_sys_member_info")
public class BusiSysMemberInfoPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 权益标题
     */
    @Column(name = "member_title")
    private String memberTitle;

    /**
     * 权益内容
     */
    @Column(name = "member_content")
    private String memberContent;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 权益相关图片
     */
    @Column(name = "member_img")
    private String memberImg;

    /**
     * 权益相关banner
     */
    @Column(name = "member_banner")
    private String memberBanner;

    /**
     * 0=显示 1=不显示
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 所属权益
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 权益等级
     */
    @Column(name = "member_level")
    private Short memberLevel;

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
     * @return create_user
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * @return update_user
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取权益标题
     *
     * @return member_title - 权益标题
     */
    public String getMemberTitle() {
        return memberTitle;
    }

    /**
     * 设置权益标题
     *
     * @param memberTitle 权益标题
     */
    public void setMemberTitle(String memberTitle) {
        this.memberTitle = memberTitle;
    }

    /**
     * 获取权益内容
     *
     * @return member_content - 权益内容
     */
    public String getMemberContent() {
        return memberContent;
    }

    /**
     * 设置权益内容
     *
     * @param memberContent 权益内容
     */
    public void setMemberContent(String memberContent) {
        this.memberContent = memberContent;
    }

    /**
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取权益相关图片
     *
     * @return member_img - 权益相关图片
     */
    public String getMemberImg() {
        return memberImg;
    }

    /**
     * 设置权益相关图片
     *
     * @param memberImg 权益相关图片
     */
    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
    }

    /**
     * 获取权益相关banner
     *
     * @return member_banner - 权益相关banner
     */
    public String getMemberBanner() {
        return memberBanner;
    }

    /**
     * 设置权益相关banner
     *
     * @param memberBanner 权益相关banner
     */
    public void setMemberBanner(String memberBanner) {
        this.memberBanner = memberBanner;
    }

    /**
     * 获取0=显示 1=不显示
     *
     * @return is_show - 0=显示 1=不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置0=显示 1=不显示
     *
     * @param isShow 0=显示 1=不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取0 正常 1 已删除
     *
     * @return del_flag - 0 正常 1 已删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置0 正常 1 已删除
     *
     * @param delFlag 0 正常 1 已删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取所属权益
     *
     * @return parent_id - 所属权益
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置所属权益
     *
     * @param parentId 所属权益
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取权益等级
     *
     * @return member_level - 权益等级
     */
    public Short getMemberLevel() {
        return memberLevel;
    }

    /**
     * 设置权益等级
     *
     * @param memberLevel 权益等级
     */
    public void setMemberLevel(Short memberLevel) {
        this.memberLevel = memberLevel;
    }
}