package com.yinhetianze.systemservice.system.model;

import java.util.List;
import java.util.Map;

public class ClassifyModelDto {

    private Integer id;

    /**
     * 分类名称
     */
    private String classifyName;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 是否显示（0隐藏，1显示）
     */
    private Short isShow;
    /**
     * 帮助中心分类的图片
     */
    private String classifyImg;

    public String getClassifyImg() {
        return classifyImg;
    }

    public void setClassifyImg(String classifyImg) {
        this.classifyImg = classifyImg;
    }

    private List<Map<String,Object>> questionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Short getIsShow() {
        return isShow;
    }

    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    public List<Map<String, Object>> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Map<String, Object>> questionList) {
        this.questionList = questionList;
    }
}
