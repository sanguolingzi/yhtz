package com.yinhetianze.systemservice.system.model;


import com.yinhetianze.core.business.httprequest.PageModel;

public class ConcatenatedCodeModel extends PageModel{
    private Integer id;

    /**
     *  0 显示  1 不显示
     */
    private Short isShow;

    /**
     * 名称
     */
    private String concatenatedName;

    /**
     * 跳转链接
     */
    private String jumpLink;

    /**
     * 0 正常 1 已删除
     */
    private Short delFlag;

    private Short concatenatedType;

    public Short getConcatenatedType() {
        return concatenatedType;
    }

    public void setConcatenatedType(Short concatenatedType) {
        this.concatenatedType = concatenatedType;
    }

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
     * 获取 0 显示  1 不显示
     *
     * @return is_show -  0 显示  1 不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置 0 显示  1 不显示
     *
     * @param isShow  0 显示  1 不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取名称
     *
     * @return concatenated_name - 名称
     */
    public String getConcatenatedName() {
        return concatenatedName;
    }

    /**
     * 设置名称
     *
     * @param concatenatedName 名称
     */
    public void setConcatenatedName(String concatenatedName) {
        this.concatenatedName = concatenatedName;
    }

    /**
     * 获取跳转链接
     *
     * @return jump_link - 跳转链接
     */
    public String getJumpLink() {
        return jumpLink;
    }

    /**
     * 设置跳转链接
     *
     * @param jumpLink 跳转链接
     */
    public void setJumpLink(String jumpLink) {
        this.jumpLink = jumpLink;
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