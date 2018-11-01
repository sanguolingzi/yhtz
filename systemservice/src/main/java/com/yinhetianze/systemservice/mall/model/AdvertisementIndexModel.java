package com.yinhetianze.systemservice.mall.model;


import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AdvertisementIndexModel {

    private Integer id;

    /**
     * 广告名称
     */
    private String advertisementName;



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
     * 获取广告名称
     *
     * @return advertisement_name - 广告名称
     */
    public String getAdvertisementName() {
        return advertisementName;
    }

    /**
     * 设置广告名称
     *
     * @param advertisementName 广告名称
     */
    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    private List<Map> advertisementDetail;

    public List<Map> getAdvertisementDetail() {
        return advertisementDetail;
    }

    public void setAdvertisementDetail(List<Map> advertisementDetail) {
        this.advertisementDetail = advertisementDetail;
    }
}
