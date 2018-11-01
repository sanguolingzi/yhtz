package com.yinhetianze.cachedata.area;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 地址信息缓存值对象
 */
public class AreaCacheDataModel implements Serializable
{
    /**
     * 所有缓存的地区信息键值列表
     */
    private Map<String, AreaInfo> areaInfoMap;

    /**
     * 国家信息
     */
    private Map<String, AreaInfo> countryMap;

    /**
     * 包含完整的城市地县级联关系的省份信息
     */
    private Map<String, AreaInfo> provinceMap;

    /**
     * 包含完整的地县级联关系的城市信息
     */
    private Map<String, AreaInfo> cityMap;

    /**
     * 地县信息
     */
    private Map<String, AreaInfo> countyMap;

    /**
     * 省份列表，不包含级联关系
     */
    private List<AreaInfo> provinceList;

    /**
     * 城市列表，不包含级联关系
     */
    private List<AreaInfo> cityList;

    /**
     * 地县列表，不包含级联关系
     */
    private List<AreaInfo> countyList;

    public Map<String, AreaInfo> getCountryMap()
    {
        return countryMap;
    }

    public void setCountryMap(Map<String, AreaInfo> countryMap)
    {
        this.countryMap = countryMap;
    }

    public Map<String, AreaInfo> getProvinceMap()
    {
        return provinceMap;
    }

    public void setProvinceMap(Map<String, AreaInfo> provinceMap)
    {
        this.provinceMap = provinceMap;
    }

    public Map<String, AreaInfo> getCityMap()
    {
        return cityMap;
    }

    public void setCityMap(Map<String, AreaInfo> cityMap)
    {
        this.cityMap = cityMap;
    }

    public Map<String, AreaInfo> getCountyMap()
    {
        return countyMap;
    }

    public void setCountyMap(Map<String, AreaInfo> countyMap)
    {
        this.countyMap = countyMap;
    }

    public List<AreaInfo> getProvinceList()
    {
        return provinceList;
    }

    public void setProvinceList(List<AreaInfo> provinceList)
    {
        this.provinceList = provinceList;
    }

    public List<AreaInfo> getCityList()
    {
        return cityList;
    }

    public void setCityList(List<AreaInfo> cityList)
    {
        this.cityList = cityList;
    }

    public List<AreaInfo> getCountyList()
    {
        return countyList;
    }

    public void setCountyList(List<AreaInfo> countyList)
    {
        this.countyList = countyList;
    }

    public Map<String, AreaInfo> getAreaInfoMap()
    {
        return areaInfoMap;
    }

    public void setAreaInfoMap(Map<String, AreaInfo> areaInfoMap)
    {
        this.areaInfoMap = areaInfoMap;
    }
}
