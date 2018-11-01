package com.yinhetianze.pojo.order;

import javax.persistence.*;

@Table(name = "busi_express_code")
public class ExpressCodePojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 快递公司
     */
    @Column(name = "express_company")
    private String expressCompany;

    /**
     * 编码
     */
    @Column(name = "express_code")
    private String expressCode;

    /**
     * 轨迹查询 0.不支持 1.支持
     */
    @Column(name = "express_track")
    private String expressTrack;

    /**
     * 电子面单 0.不支持 1.支持
     */
    @Column(name = "express_single")
    private String expressSingle;

    /**
     * 预约取件 0.不支持 1.支持
     */
    @Column(name = "express_delivery")
    private String expressDelivery;

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
     * 获取快递公司
     *
     * @return express_company - 快递公司
     */
    public String getExpressCompany() {
        return expressCompany;
    }

    /**
     * 设置快递公司
     *
     * @param expressCompany 快递公司
     */
    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    /**
     * 获取编码
     *
     * @return express_code - 编码
     */
    public String getExpressCode() {
        return expressCode;
    }

    /**
     * 设置编码
     *
     * @param expressCode 编码
     */
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    /**
     * 获取轨迹查询 0.不支持 1.支持
     *
     * @return express_track - 轨迹查询 0.不支持 1.支持
     */
    public String getExpressTrack() {
        return expressTrack;
    }

    /**
     * 设置轨迹查询 0.不支持 1.支持
     *
     * @param expressTrack 轨迹查询 0.不支持 1.支持
     */
    public void setExpressTrack(String expressTrack) {
        this.expressTrack = expressTrack;
    }

    /**
     * 获取电子面单 0.不支持 1.支持
     *
     * @return express_single - 电子面单 0.不支持 1.支持
     */
    public String getExpressSingle() {
        return expressSingle;
    }

    /**
     * 设置电子面单 0.不支持 1.支持
     *
     * @param expressSingle 电子面单 0.不支持 1.支持
     */
    public void setExpressSingle(String expressSingle) {
        this.expressSingle = expressSingle;
    }

    /**
     * 获取预约取件 0.不支持 1.支持
     *
     * @return express_delivery - 预约取件 0.不支持 1.支持
     */
    public String getExpressDelivery() {
        return expressDelivery;
    }

    /**
     * 设置预约取件 0.不支持 1.支持
     *
     * @param expressDelivery 预约取件 0.不支持 1.支持
     */
    public void setExpressDelivery(String expressDelivery) {
        this.expressDelivery = expressDelivery;
    }
}