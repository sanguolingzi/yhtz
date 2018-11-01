package com.yinhetianze.systemservice.mall.model;

import java.util.List;
import java.util.Map;

public class FloorMobileIndexModel {
    private List<Map> FloorDetail;

    public List<Map> getFloorDetail() {
        return FloorDetail;
    }

    public void setFloorDetail(List<Map> floorDetail) {
        FloorDetail = floorDetail;
    }
    private Integer id;

    /**
     * 楼层名称
     */

    private String floorName;

    private String icon;

    /**
     * 排序号
     */
    private Short sort;
    /**
     * 楼层大图
     */
    private String photoUrl;

    /**
     * 链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情
     */
    private Short linkMarkup;
    /**
     * 链接参数
     */
    private String linkParameter;
    /**
     * 楼层链接
     */
    private String floorLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public String getFloorLink() {
        return floorLink;
    }

    public void setFloorLink(String floorLink) {
        this.floorLink = floorLink;
    }
    /**
     * 0 手机端 1 PC端
     */
    private Short floorType;

    public Short getFloorType() {
        return floorType;
    }

    public void setFloorType(Short floorType) {
        this.floorType = floorType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
