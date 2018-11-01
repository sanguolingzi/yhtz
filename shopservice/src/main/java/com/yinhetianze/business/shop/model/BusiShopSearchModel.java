package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusiShopSearchModel extends PageModel {
    private String searchContent;

    private String orderSort = "desc";

    private Integer id;
    /**
     *  pc,wap
     */
    private String type;

    private String shopName;

    private String shopMainProduct;

    private String shopLogo;

    private String shopMemo;

    private String regionLocation;

    private Integer productTotal;

    /**
     * 店铺相关商品列表 取前5个
     * {"img":"","productId":""}
     */
    List<Map<String,Object>> productList = new ArrayList<>();

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String sort) {
        this.orderSort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopMemo() {
        return shopMemo;
    }

    public void setShopMemo(String shopMemo) {
        this.shopMemo = shopMemo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopMainProduct() {
        return shopMainProduct;
    }

    public void setShopMainProduct(String shopMainProduct) {
        this.shopMainProduct = shopMainProduct;
    }
}