package com.yinhetianze.pojo.category;

import javax.persistence.*;

@Table(name = "busi_classify_img")
public class ClassifyImgPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分类图片地址
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 所属一级分类
     */
    @Column(name = "classify_id")
    private Integer classifyId;

    /**
     * 排序号
     */
    private Short sort;

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
     * 获取分类图片地址
     *
     * @return img_url - 分类图片地址
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置分类图片地址
     *
     * @param imgUrl 分类图片地址
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取所属一级分类
     *
     * @return classify_id - 所属一级分类
     */
    public Integer getClassifyId() {
        return classifyId;
    }

    /**
     * 设置所属一级分类
     *
     * @param classifyId 所属一级分类
     */
    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
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