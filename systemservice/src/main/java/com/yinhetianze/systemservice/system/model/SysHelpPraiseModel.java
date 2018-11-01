package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SysHelpPraiseModel extends PageModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 问答ID
     */
    @Column(name = "question_id")
    private Integer questionId;

    /**
     * 点赞状态（1已点赞，2没帮助,3已取消）
     */
    @Column(name = "praise_status")
    private Short praiseStatus;

    /**
     * 获取点赞ID
     *
     * @return id - 点赞ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置点赞ID
     *
     * @param id 点赞ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return customer_id - 用户ID
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置用户ID
     *
     * @param customerId 用户ID
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取问答ID
     *
     * @return question_id - 问答ID
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * 设置问答ID
     *
     * @param questionId 问答ID
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * 获取点赞状态（1已点赞，2没帮助,3已取消）
     *
     * @return praise_status - 点赞状态（1已点赞，2没帮助,3已取消）
     */
    public Short getPraiseStatus() {
        return praiseStatus;
    }

    /**
     * 设置点赞状态（1已点赞，2没帮助,3已取消）
     *
     * @param praiseStatus 点赞状态（1已点赞，2没帮助,3已取消）
     */
    public void setPraiseStatus(Short praiseStatus) {
        this.praiseStatus = praiseStatus;
    }
}
