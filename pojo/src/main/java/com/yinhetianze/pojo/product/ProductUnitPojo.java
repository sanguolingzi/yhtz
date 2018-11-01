package com.yinhetianze.pojo.product;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_unit")
public class ProductUnitPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 单位名称
     */
    @Column(name = "unit_name")
    private String unitName;

    /**
     * 排序编号
     */
    private Short sort;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否显示，1=显示，0=不显示，默认1显示
     */
    @Column(name = "is_show")
    private Short isShow;
    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
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