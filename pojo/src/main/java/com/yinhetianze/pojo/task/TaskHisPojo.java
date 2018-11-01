package com.yinhetianze.pojo.task;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_sys_task_his")
public class TaskHisPojo {
    /**
     * 任务ID
     */
    @Column(name = "task_id")
    private Integer taskId;

    /**
     * 开始执行时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 结束执行时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 执行状态：1=开始，2=完成，3=异常
     */
    private Short state;

    /**
     * 记录备注信息
     */
    private String remarks;

    /**
     * 获取任务ID
     *
     * @return task_id - 任务ID
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * 设置任务ID
     *
     * @param taskId 任务ID
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取开始执行时间
     *
     * @return begin_time - 开始执行时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置开始执行时间
     *
     * @param beginTime 开始执行时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取结束执行时间
     *
     * @return end_time - 结束执行时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束执行时间
     *
     * @param endTime 结束执行时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取执行状态：1=开始，2=完成，3=异常
     *
     * @return state - 执行状态：1=开始，2=完成，3=异常
     */
    public Short getState() {
        return state;
    }

    /**
     * 设置执行状态：1=开始，2=完成，3=异常
     *
     * @param state 执行状态：1=开始，2=完成，3=异常
     */
    public void setState(Short state) {
        this.state = state;
    }

    /**
     * 获取记录备注信息
     *
     * @return remarks - 记录备注信息
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置记录备注信息
     *
     * @param remarks 记录备注信息
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}