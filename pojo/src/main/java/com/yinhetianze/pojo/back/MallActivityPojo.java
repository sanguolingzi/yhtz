package com.yinhetianze.pojo.back;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_mall_activity")
public class MallActivityPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 活动名称
     */
    @Column(name = "activityName")
    private String activityname;

    /**
     * 活动图片
     */
    @Column(name = "activityImage")
    private String activityimage;

    /**
     * 活动链接
     */
    @Column(name = "activityLink")
    private String activitylink;

    /**
     * 0 正在进行 ,1 已结束，2重新开始
     */
    @Column(name = "activityStatus")
    private Integer activitystatus;

    /**
     * 活动开始时间
     */
    @Column(name = "activityBeginDate")
    private Date activitybegindate;

    /**
     * 活动结束时间
     */
    @Column(name = "activityendDate")
    private Date activityenddate;

    /**
     * 类型(1.pc端,2.手机端)
     */
    private Integer type;

    /**
     * 活动排序
     */
    @Column(name = "activityPriority")
    private Integer activitypriority;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "create_time")
    @OrderBy(value="desc")
    private Date createTime;

    @Column(name = "update_user")
    private Integer updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

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
     * 获取活动名称
     *
     * @return activityName - 活动名称
     */
    public String getActivityname() {
        return activityname;
    }

    /**
     * 设置活动名称
     *
     * @param activityname 活动名称
     */
    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    /**
     * 获取活动图片
     *
     * @return activityImage - 活动图片
     */
    public String getActivityimage() {
        return activityimage;
    }

    /**
     * 设置活动图片
     *
     * @param activityimage 活动图片
     */
    public void setActivityimage(String activityimage) {
        this.activityimage = activityimage;
    }

    /**
     * 获取活动链接
     *
     * @return activityLink - 活动链接
     */
    public String getActivitylink() {
        return activitylink;
    }

    /**
     * 设置活动链接
     *
     * @param activitylink 活动链接
     */
    public void setActivitylink(String activitylink) {
        this.activitylink = activitylink;
    }

    /**
     * 获取0 正在进行 ,1 已结束，2重新开始
     *
     * @return activityStatus - 0 正在进行 ,1 已结束，2重新开始
     */
    public Integer getActivitystatus() {
        return activitystatus;
    }

    /**
     * 设置0 正在进行 ,1 已结束，2重新开始
     *
     * @param activitystatus 0 正在进行 ,1 已结束，2重新开始
     */
    public void setActivitystatus(Integer activitystatus) {
        this.activitystatus = activitystatus;
    }

    /**
     * 获取活动开始时间
     *
     * @return activityBeginDate - 活动开始时间
     */
    public Date getActivitybegindate() {
        return activitybegindate;
    }

    /**
     * 设置活动开始时间
     *
     * @param activitybegindate 活动开始时间
     */
    public void setActivitybegindate(Date activitybegindate) {
        this.activitybegindate = activitybegindate;
    }

    /**
     * 获取活动结束时间
     *
     * @return activityendDate - 活动结束时间
     */
    public Date getActivityenddate() {
        return activityenddate;
    }

    /**
     * 设置活动结束时间
     *
     * @param activityenddate 活动结束时间
     */
    public void setActivityenddate(Date activityenddate) {
        this.activityenddate = activityenddate;
    }

    /**
     * 获取类型(1.pc端,2.手机端)
     *
     * @return type - 类型(1.pc端,2.手机端)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型(1.pc端,2.手机端)
     *
     * @param type 类型(1.pc端,2.手机端)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取活动排序
     *
     * @return activityPriority - 活动排序
     */
    public Integer getActivitypriority() {
        return activitypriority;
    }

    /**
     * 设置活动排序
     *
     * @param activitypriority 活动排序
     */
    public void setActivitypriority(Integer activitypriority) {
        this.activitypriority = activitypriority;
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
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
}