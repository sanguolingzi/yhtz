package com.yinhetianze.pojo.task;

import javax.persistence.*;

@Table(name = "busi_sys_area")
public class AreaPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 省市县编码
     */
    private String code;

    /**
     * 省市区名称
     */
    private String name;

    /**
     * 上级id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 简称
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 级别类型
     */
    @Column(name = "level_type")
    private Short levelType;

    /**
     * 城市区号
     */
    @Column(name = "city_code")
    private String cityCode;

    /**
     * 邮编
     */
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 组合全称
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 全称拼音
     */
    private String pinyin;

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
     * 获取省市县编码
     *
     * @return code - 省市县编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置省市县编码
     *
     * @param code 省市县编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取省市区名称
     *
     * @return name - 省市区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置省市区名称
     *
     * @param name 省市区名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取上级id
     *
     * @return parent_id - 上级id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置上级id
     *
     * @param parentId 上级id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取简称
     *
     * @return short_name - 简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置简称
     *
     * @param shortName 简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 获取级别类型
     *
     * @return level_type - 级别类型
     */
    public Short getLevelType() {
        return levelType;
    }

    /**
     * 设置级别类型
     *
     * @param levelType 级别类型
     */
    public void setLevelType(Short levelType) {
        this.levelType = levelType;
    }

    /**
     * 获取城市区号
     *
     * @return city_code - 城市区号
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置城市区号
     *
     * @param cityCode 城市区号
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 获取邮编
     *
     * @return zip_code - 邮编
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     *
     * @param zipCode 邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取组合全称
     *
     * @return full_name - 组合全称
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 设置组合全称
     *
     * @param fullName 组合全称
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 获取经度
     *
     * @return lng - 经度
     */
    public String getLng() {
        return lng;
    }

    /**
     * 设置经度
     *
     * @param lng 经度
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

    /**
     * 获取纬度
     *
     * @return lat - 纬度
     */
    public String getLat() {
        return lat;
    }

    /**
     * 设置纬度
     *
     * @param lat 纬度
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * 获取全称拼音
     *
     * @return pinyin - 全称拼音
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置全称拼音
     *
     * @param pinyin 全称拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}