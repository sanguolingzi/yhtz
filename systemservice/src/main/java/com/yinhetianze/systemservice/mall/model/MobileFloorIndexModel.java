package com.yinhetianze.systemservice.mall.model;

import java.util.List;
import java.util.Map;

public class MobileFloorIndexModel {
    private Integer id;
    /**
     * 排序号
     */
    private Short sort;

    /**
     * 链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     */
    private Short linkMarkup;

    /**
     * 链接参数
     */
    private String linkParameter;

    /**
     * 手机楼层链接
     */
    private String mobileFloorLink;

    /**
     * 楼层名称
     */
    private String mobileFloorName;
    /**
     * 图片地址
     */
    private String photoUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Short getLinkMarkup() {
        return linkMarkup;
    }

    public void setLinkMarkup(Short linkMarkup) {
        this.linkMarkup = linkMarkup;
    }

    public String getLinkParameter() {
        return linkParameter;
    }

    public void setLinkParameter(String linkParameter) {
        this.linkParameter = linkParameter;
    }

    public String getMobileFloorLink() {
        return mobileFloorLink;
    }

    public void setMobileFloorLink(String mobileFloorLink) {
        this.mobileFloorLink = mobileFloorLink;
    }

    public String getMobileFloorName() {
        return mobileFloorName;
    }

    public void setMobileFloorName(String mobileFloorName) {
        this.mobileFloorName = mobileFloorName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    private List<Map> mobileFloor;

    public List<Map> getMobileFloor() {
        return mobileFloor;
    }

    public void setMobileFloor(List<Map> mobileFloor) {
        this.mobileFloor = mobileFloor;
    }
}
