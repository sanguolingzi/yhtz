package com.yinhetianze.systemservice.mall.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;

public class FloorDetailProductPageModel extends PageModel {
    private Integer id;

    /**
     * 所属楼层id
     */
    private Integer floorId;
    /**
     * 商品名字
     */
    private  String prodName;

    /**
     * 上架时间
     */
    private Short shelfTime;
    /**
     * 价格高低
     */
    private Short sellPrice;
    /**
     * 最低价
     */
    private BigDecimal minsellPrice;
    /**
     * 最高价
     */
    private BigDecimal maxsellPrice;

    /**
     * 销量高低
     */
    private  Short salesVolume;

    public Short getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Short salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Short getShelfTime() {
        return shelfTime;
    }

    public void setShelfTime(Short shelfTime) {
        this.shelfTime = shelfTime;
    }

    public Short getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Short sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getMinsellPrice() {
        return minsellPrice;
    }

    public void setMinsellPrice(BigDecimal minsellPrice) {
        this.minsellPrice = minsellPrice;
    }

    public BigDecimal getMaxsellPrice() {
        return maxsellPrice;
    }

    public void setMaxsellPrice(BigDecimal maxsellPrice) {
        this.maxsellPrice = maxsellPrice;
    }

}
