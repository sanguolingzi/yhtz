package com.yinhetianze.pojo.task;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_sys_task")
public class TaskPojo {
    @Id
    @Column(name = "task_id")
    private Integer taskId;

    /**
     * 任务名称
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     * 任务组
     */
    @Column(name = "task_group")
    private String taskGroup;

    /**
     * 任务实现类
     */
    @Column(name = "task_class")
    private String taskClass;

    /**
     * 任务类型，1=定时任务，2=计划任务，默认1定时任务
     */
    @Column(name = "task_type")
    private Short taskType;

    /**
     * 定时时间，task_type=1时不能为空
     */
    @Column(name = "task_period")
    private Integer taskPeriod;

    /**
     * 计划表达式，task_type=2时不能为空
     */
    @Column(name = "task_cron")
    private String taskCron;

    /**
     * 是否有效，1=有效，0=无效，默认1有效
     */
    private Short status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否系统启动时自动启动
     */
    @Column(name = "is_initial")
    private Short isInitial;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return task_id
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取任务名称
     *
     * @return task_name - 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     *
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务组
     *
     * @return task_group - 任务组
     */
    public String getTaskGroup() {
        return taskGroup;
    }

    /**
     * 设置任务组
     *
     * @param taskGroup 任务组
     */
    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    /**
     * 获取任务实现类
     *
     * @return task_class - 任务实现类
     */
    public String getTaskClass() {
        return taskClass;
    }

    /**
     * 设置任务实现类
     *
     * @param taskClass 任务实现类
     */
    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    /**
     * 获取任务类型，1=定时任务，2=计划任务，默认1定时任务
     *
     * @return task_type - 任务类型，1=定时任务，2=计划任务，默认1定时任务
     */
    public Short getTaskType() {
        return taskType;
    }

    /**
     * 设置任务类型，1=定时任务，2=计划任务，默认1定时任务
     *
     * @param taskType 任务类型，1=定时任务，2=计划任务，默认1定时任务
     */
    public void setTaskType(Short taskType) {
        this.taskType = taskType;
    }

    /**
     * 获取定时时间，task_type=1时不能为空
     *
     * @return task_period - 定时时间，task_type=1时不能为空
     */
    public Integer getTaskPeriod() {
        return taskPeriod;
    }

    /**
     * 设置定时时间，task_type=1时不能为空
     *
     * @param taskPeriod 定时时间，task_type=1时不能为空
     */
    public void setTaskPeriod(Integer taskPeriod) {
        this.taskPeriod = taskPeriod;
    }

    /**
     * 获取计划表达式，task_type=2时不能为空
     *
     * @return task_cron - 计划表达式，task_type=2时不能为空
     */
    public String getTaskCron() {
        return taskCron;
    }

    /**
     * 设置计划表达式，task_type=2时不能为空
     *
     * @param taskCron 计划表达式，task_type=2时不能为空
     */
    public void setTaskCron(String taskCron) {
        this.taskCron = taskCron;
    }

    /**
     * 获取是否有效，1=有效，0=无效，默认1有效
     *
     * @return status - 是否有效，1=有效，0=无效，默认1有效
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置是否有效，1=有效，0=无效，默认1有效
     *
     * @param status 是否有效，1=有效，0=无效，默认1有效
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取是否系统启动时自动启动
     *
     * @return is_initial - 是否系统启动时自动启动
     */
    public Short getIsInitial() {
        return isInitial;
    }

    /**
     * 设置是否系统启动时自动启动
     *
     * @param isInitial 是否系统启动时自动启动
     */
    public void setIsInitial(Short isInitial) {
        this.isInitial = isInitial;
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
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}