package com.yinhetianze.pojo.back;

import javax.persistence.*;

@Table(name = "busi_sys_help_question")
public class QuestionPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 问答标题
     */
    @Column(name = "question_name")
    private String questionName;

    /**
     * 问答副标题
     */
    @Column(name = "question_subTitle")
    private  String questionSubTitle;

    /**
     * 问题答案
     */
    @Column(name = "question_answer")
    private String questionAnswer;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 是否显示（0隐藏，1显示）
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 分类ID
     */
    @Column(name = "classify_id")
    private Integer classifyId;

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
     * 获取问答标题
     *
     * @return question_name - 问答标题
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * 设置问答标题
     *
     * @param questionName 问答标题
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    /**
     * 获取问题答案
     *
     * @return question_answer - 问题答案
     */
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    /**
     * 设置问题答案
     *
     * @param questionAnswer 问题答案
     */
    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
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
     * 获取分类ID
     *
     * @return classify_id - 分类ID
     */
    public Integer getClassifyId() {
        return classifyId;
    }

    /**
     * 设置分类ID
     *
     * @param classifyId 分类ID
     */
    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    /**
     * 设置商品副标题
     *
     * @return question_subTitle 问答副标题
     */
    public String getQuestionSubTitle() {
        return questionSubTitle;
    }

    /**
     * 获取问答副标题
     *
     * @param questionSubTitle 问答副标题
     */
    public void setQuestionSubTitle(String questionSubTitle) {
        this.questionSubTitle = questionSubTitle;
    }

}