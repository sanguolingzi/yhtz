package com.yinhetianze.common.business.sys.area.cachedata;

import com.yinhetianze.cachedata.area.AreaCacheDataModel;
import com.yinhetianze.cachedata.area.AreaInfo;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 地区缓存数据工具类
 * 使用时在config中使用@Bean注解注入
 */
@Service
public class AreaModelUtils
{
    @Autowired
    private CacheData<AreaCacheDataModel> areaCacheData;

    /**
     * 内部根据编码从指定的数据库中筛选
     * @param areaCode
     * @param areaInfoMap
     * @return
     */
    private AreaInfo getAreaInfoByCode(String areaCode, Map<String, AreaInfo> areaInfoMap)
    {
        if (CommonUtil.isNotEmpty(areaCode) && CommonUtil.isNotEmpty(areaInfoMap))
        {
            return areaInfoMap.get(areaCode);
        }
        return null;
    }

    /**
     * 获取省份信息
     * @param provinceId
     * @param isContainCity
     * @return
     * @throws Exception
     */
    public final AreaInfo getProvince(String provinceId, boolean isContainCity) throws Exception
    {
        AreaCacheDataModel model = areaCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            Map<String, AreaInfo> provinceMap = model.getProvinceMap();
            AreaInfo ai = getAreaInfoByCode(provinceId, provinceMap);
            if (CommonUtil.isNotEmpty(ai))
            {
                return isContainCity ? ai : ai.clone();
            }
        }

        return null;
    }

    /**
     * 获取城市信息
     * @param cityCode
     * @param isContainCounty
     * @return
     * @throws Exception
     */
    public final AreaInfo getCity(String cityCode, boolean isContainCounty) throws Exception
    {
        AreaCacheDataModel model = areaCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            Map<String, AreaInfo> cityMap = model.getCityMap();
            AreaInfo ai = getAreaInfoByCode(cityCode, cityMap);
            if (CommonUtil.isNotEmpty(ai))
            {
                return isContainCounty ? ai : ai.clone();
            }
        }
        return null;
    }

    /**
     * 获取地县信息
     * @param countyCode
     * @return
     * @throws Exception
     */
    public final AreaInfo getCounty(String countyCode) throws Exception
    {
        AreaCacheDataModel model = areaCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            Map<String, AreaInfo> cityMap = model.getCountyMap();
            return getAreaInfoByCode(countyCode, cityMap);
        }
        return null;
    }

    /**
     * 获取单独的身份信息列表
     * @return
     */
    public final List<AreaInfo> getProvinceList()
    {
        try
        {
            AreaCacheDataModel model = areaCacheData.getCacheData();

            if (CommonUtil.isNotEmpty(model))
            {
                return model.getProvinceList();
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(AreaModelUtils.class, e);
        }
        return null;
    }

    /**
     * 获取单独的城市信息列表
     * @return
     */
    public final List<AreaInfo> getCityList() throws Exception
    {
        AreaCacheDataModel model = areaCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            return model.getCityList();
        }
        return null;
    }

    /**
     * 获取地县信息列表
     * @return
     */
    public final List<AreaInfo> getCountyList()
    {
        AreaCacheDataModel model = areaCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            return model.getCountyList();
        }
        return null;
    }

    /**
     * 获取缓存信息map集合
     * @return
     */
    public final Map<String, AreaInfo> getAreaInfoMap()
    {
        AreaCacheDataModel model = areaCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            return model.getAreaInfoMap();
        }
        return null;
    }

    /**
     * 获取地区信息列表
     * @return
     */
    public final Collection<AreaInfo> getAreaInfoList()
    {
        Map<String, AreaInfo> areaInfoMap = getAreaInfoMap();
        if (CommonUtil.isNotEmpty(areaInfoMap))
        {
            return areaInfoMap.values();
        }
        return null;
    }

}
