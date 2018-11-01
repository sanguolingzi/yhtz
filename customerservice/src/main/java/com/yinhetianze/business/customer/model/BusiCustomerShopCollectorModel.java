package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusiCustomerShopCollectorModel extends PageModel{
    private Integer id;

    /**
     * 0 商品收藏 1店铺收藏
     */
    private Short cType;

    /**
     * 关联数据id
     */
    private Integer relationId;


    private String name;


    private String imgSrc;


    private String memo;

    private String regionLocation;

    private Integer productTotal;

    /**
     * 店铺相关商品列表 取前5个
     * {"img":"","productId":""}
     */
    List<Map<String,Object>> productList = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getcType() {
        return cType;
    }

    public void setcType(Short cType) {
        this.cType = cType;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRegionLocation() {
        return regionLocation;
    }

    public void setRegionLocation(String regionLocation) {
        this.regionLocation = regionLocation;
    }

    public Integer getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Integer productTotal) {
        this.productTotal = productTotal;
    }

    public List<Map<String, Object>> getProductList() {
        return productList;
    }

    public void setProductList(List<Map<String, Object>> productList) {
        this.productList = productList;
    }
}