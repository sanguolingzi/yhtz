package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httprequest.PageModel;

import javax.persistence.Column;

import java.util.Date;

public class ProductUnitModel extends PageModel{

    private Integer id;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 排序编号
     */
    private Short sort;


    private Date createTime;

    /**
     * 是否显示，1=显示，0=不显示，默认1显示
     */
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */
    private Short delFlag;

    public Short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
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
     * 获取单位名称
     *
     * @return unit_name - 单位名称
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * 设置单位名称
     *
     * @param unitName 单位名称
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * 获取排序编号
     *
     * @return sort - 排序编号
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序编号
     *
     * @param sort 排序编号
     */
    public void setSort(Short sort) {
        this.sort = sort;
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
     * 获取是否显示，1=显示，0=不显示，默认1显示
     *
     * @return is_show - 是否显示，1=显示，0=不显示，默认1显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示，1=显示，0=不显示，默认1显示
     *
     * @param isShow 是否显示，1=显示，0=不显示，默认1显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }
}
