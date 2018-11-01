package com.yinhetianze.common.business.sys.sysproperties.cachedata;

import com.yinhetianze.cachedata.sysproperties.SysPropertiesCacheDataModel;
import com.yinhetianze.cachedata.sysproperties.SysPropertiesInfo;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 系统配置参数工具类
 * 使用时在配置中添加@Bean注入
 */
@Service
public class SysPropertiesUtils
{
    @Autowired
    private CacheData<SysPropertiesCacheDataModel> sysPropertiesCacheData ;

    /**
     * 获取缓存数据
     * @return
     */
    private SysPropertiesCacheDataModel getCacheData()
    {
        SysPropertiesCacheDataModel cacheData = null;

        if (CommonUtil.isNotEmpty(sysPropertiesCacheData))
        {
            return sysPropertiesCacheData.getCacheData();
        }

        return cacheData;
    }

    /**
     * 根据id(参数名称)获取参数值，获取不到返回null
     * @param id
     * @return
     * @throws Exception
     */
    private final SysPropertiesInfo getPropertiesById(String id) throws Exception
    {
        SysPropertiesCacheDataModel model = sysPropertiesCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            Map<String, SysPropertiesInfo> map = model.getSysPropertiesInfoMap();
            if (CommonUtil.isNotEmpty(map))
            {
                return map.get(id);
            }
        }
        return null;
    }

    /**
     * 根据id获取整数值，获取不到返回null
     * @param id
     * @return
     */
    public final Integer getIntValue(String id)
    {
        return getIntValue(id, null);
    }

    /**
     * 根据id获取整数值，获取不到时返回defaultValue
     * @param id
     * @param defaultValue
     * @return
     */
    public final Integer getIntValue(String id, Integer defaultValue)
    {
        try
        {
            SysPropertiesInfo info = getPropertiesById(id);
            if (CommonUtil.isNotEmpty(info))
            {
                return Integer.parseInt(info.getValue());
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(SysPropertiesUtils.class, e);
        }

        return defaultValue;
    }

    /**
     * 根据id获取布尔值，获取不到时返回null
     * @param id
     * @return
     */
    public final Boolean getBoolValue(String id)
    {
        return getBoolValue(id, null);
    }

    /**
     * 根据id获取布尔值，获取不到时返回defaultValue
     * @param id
     * @param defaultValue
     * @return
     */
    public final Boolean getBoolValue(String id, Boolean defaultValue)
    {
        try
        {
            SysPropertiesInfo info = getPropertiesById(id);
            if (CommonUtil.isNotEmpty(info))
            {
                return Boolean.parseBoolean(info.getValue());
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(SysPropertiesUtils.class, e);
        }

        return defaultValue;
    }

    /**
     * 根据id获取对应的参数值，获取不到时返回null
     * @param id
     * @return
     */
    public final String getStringValue(String id)
    {
        return getStringValue(id, null);
    }

    /**
     * 根据id获取对应的参数值，获取不到时返回defaultValue
     * @param id
     * @param defaultValue
     * @return
     */
    public final String getStringValue(String id, String defaultValue)
    {
        try
        {
            SysPropertiesInfo info = getPropertiesById(id);
            if (CommonUtil.isNotEmpty(info))
            {
                return info.getValue();
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(SysPropertiesUtils.class, e);
        }
        return defaultValue;
    }

    /**
     * 根据id（参数名称）获取对应的参数值，获取不到时返回null
     * @param id
     * @return
     */
    public final BigDecimal getDecimalValue(String id)
    {
        return getDecimalValue(id, null);
    }

    /**
     * 根据id（参数名称）获取对应的参数值，获取不到时返回defaultValue
     * @param id
     * @param defaultValue
     * @return
     */
    public final BigDecimal getDecimalValue(String id, BigDecimal defaultValue)
    {
        try
        {
            SysPropertiesInfo info = getPropertiesById(id);
            if (CommonUtil.isNotEmpty(info))
            {
                return new BigDecimal(info.getValue());
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(SysPropertiesUtils.class, e);
        }
        return defaultValue;
    }

}
