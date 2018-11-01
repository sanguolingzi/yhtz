package com.yinhetianze.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductInfo implements Serializable{

    String id ;
    String name;
    BigDecimal m_price;
    BigDecimal s_price;
    Date crateTime;
    Integer storage;

    public ProductInfo(String id, String name,BigDecimal m_price,BigDecimal s_price,Date crateTime,Integer storage){
        this.id = id;
        this.name = name;
        this.m_price = m_price;
        this.s_price = s_price;
        this.crateTime = crateTime;
        this.storage = storage;
    }


    public ProductInfo(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getM_price() {
        return m_price;
    }

    public void setM_price(BigDecimal m_price) {
        this.m_price = m_price;
    }

    public BigDecimal getS_price() {
        return s_price;
    }

    public void setS_price(BigDecimal s_price) {
        this.s_price = s_price;
    }

    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }
}
