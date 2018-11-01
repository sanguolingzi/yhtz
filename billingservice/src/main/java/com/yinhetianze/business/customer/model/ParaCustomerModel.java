package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.Date;

public class ParaCustomerModel extends BasicModel{
    private String phone;

    private String photoUrl;

    private String registerTime;

    private Integer recommendCount;

    private Integer marketLevel;

    private BigDecimal totalEaring;

    private BigDecimal lastMonthEaring;

    private Integer showId;

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }

    public String getRegisterTime(){
        return registerTime;
    }

    public void setRegisterTime(String registerTime){
        this.registerTime = registerTime;
    }

    public Integer getRecommendCount(){
        return recommendCount;
    }

    public void setRecommendCount(Integer recommendCount){
        this.recommendCount = recommendCount;
    }

    public Integer getMarketLevel(){
        return marketLevel;
    }

    public void setMarketLevel(Integer marketLevel){
        this.marketLevel = marketLevel;
    }


    public BigDecimal getTotalEaring(){
        return totalEaring;
    }

    public void setTotalEaring(BigDecimal totalEaring){
        this.totalEaring = totalEaring;
    }

    public BigDecimal getLastMonthEaring(){
        return lastMonthEaring;
    }

    public void setLastMonthEaring(BigDecimal lastMonthEaring){
        this.lastMonthEaring = lastMonthEaring;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }
}

