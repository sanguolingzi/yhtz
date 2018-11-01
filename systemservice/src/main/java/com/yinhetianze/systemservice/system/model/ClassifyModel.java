package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.List;
import java.util.Map;


public class ClassifyModel  extends PageModel{

    private Integer id;

    /**
     * 分类名称
     */
    private String classifyName;

    /**
     * 帮助中心分类的图片
     */
    private String classifyImg;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 是否显示（0隐藏，1显示）
     */
    private Short isShow;

    private List<Map<String,Object>> questionList;

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
     * 获取分类名称
     *
     * @return classify_name - 分类名称
     */
    public String getClassifyName() {
        return classifyName;
    }

    /**
     * 设置分类名称
     *
     * @param classifyName 分类名称
     */
    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    /**
     * 获取帮助中心分类的图片
     *
     * @return classify_img - 帮助中心分类的图片
     */
    public String getClassifyImg() {
        return classifyImg;
    }

    /**
     * 设置帮助中心分类的图片
     *
     * @param classifyImg 帮助中心分类的图片
     */
    public void setClassifyImg(String classifyImg) {
        this.classifyImg = classifyImg;
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

    public List<Map<String, Object>> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Map<String, Object>> questionList) {
        this.questionList = questionList;
    }
}