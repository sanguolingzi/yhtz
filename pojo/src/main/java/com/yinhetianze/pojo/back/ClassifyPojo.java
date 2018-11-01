package com.yinhetianze.pojo.back;

import javax.persistence.*;

@Table(name = "busi_sys_help_classify")
public class ClassifyPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分类名称
     */
    @Column(name = "classify_name")
    private String classifyName;

    /**
     * 帮助中心分类的图片
     */
    @Column(name = "classify_img")
    private String classifyImg;

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
}