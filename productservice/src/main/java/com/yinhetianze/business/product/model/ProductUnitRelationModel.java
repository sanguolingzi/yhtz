package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.product.ProductUnitPojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ProductUnitRelationModel extends BasicModel{
    /**
     * 商品计量单位ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品分类ID
     */
    private Integer categoryid;

    /**
     * 计量单位ID
     */
    private Integer unitid;

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
     * 获取商品计量单位ID
     *
     * @return id - 商品计量单位ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品计量单位ID
     *
     * @param id 商品计量单位ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品分类ID
     *
     * @return categoryId - 商品分类ID
     */
    public Integer getCategoryid() {
        return categoryid;
    }

    /**
     * 设置商品分类ID
     *
     * @param categoryid 商品分类ID
     */
    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * 获取计量单位ID
     *
     * @return unitId - 计量单位ID
     */
    public Integer getUnitid() {
        return unitid;
    }

    /**
     * 设置计量单位ID
     *
     * @param unitid 计量单位ID
     */
    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    private ProductUnitModel productUnitModel;

    public ProductUnitModel getProductUnitModel() {
        return productUnitModel;
    }

    public void setProductUnitModel(ProductUnitModel productUnitModel) {
        this.productUnitModel = productUnitModel;
    }
    private  ProductUnitPojo productUnitPojo;

    public ProductUnitPojo getProductUnitPojo() {
        return productUnitPojo;
    }

    public void setProductUnitPojo(ProductUnitPojo productUnitPojo) {
        this.productUnitPojo = productUnitPojo;
    }
}
