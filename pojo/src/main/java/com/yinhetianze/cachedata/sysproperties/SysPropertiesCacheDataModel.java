package com.yinhetianze.cachedata.sysproperties;

import java.io.Serializable;
import java.util.Map;

/**
 * 系统参数缓存信息对象
 */
public class SysPropertiesCacheDataModel implements Serializable
{
    /**
     * 系统配置参数id与对象映射集合
     */
    private Map<String, SysPropertiesInfo> sysPropertiesInfoMap;

    /**
     * 系统配置参数按照模块分类存储的集合
     */
    private Map<String, Map<String, SysPropertiesInfo>> moduleProperitesInfoMap;

    /**
     * 按照是否删除标记分类存储的系统参数集合
     */
    private Map<Boolean, Map<String, SysPropertiesInfo>> delFlagPropertiesInfoMap;

    /**
     * 按照是否有效分类存储的系统参数集合
     */
    private Map<Boolean, Map<String, SysPropertiesInfo>> isWorkPropertiesInfoMap;

    public Map<String, SysPropertiesInfo> getSysPropertiesInfoMap()
    {
        return sysPropertiesInfoMap;
    }

    public void setSysPropertiesInfoMap(Map<String, SysPropertiesInfo> sysPropertiesInfoMap)
    {
        this.sysPropertiesInfoMap = sysPropertiesInfoMap;
    }

    public Map<String, Map<String, SysPropertiesInfo>> getModuleProperitesInfoMap()
    {
        return moduleProperitesInfoMap;
    }

    public void setModuleProperitesInfoMap(Map<String, Map<String, SysPropertiesInfo>> moduleProperitesInfoMap)
    {
        this.moduleProperitesInfoMap = moduleProperitesInfoMap;
    }

    public Map<Boolean, Map<String, SysPropertiesInfo>> getDelFlagPropertiesInfoMap()
    {
        return delFlagPropertiesInfoMap;
    }

    public void setDelFlagPropertiesInfoMap(Map<Boolean, Map<String, SysPropertiesInfo>> delFlagPropertiesInfoMap)
    {
        this.delFlagPropertiesInfoMap = delFlagPropertiesInfoMap;
    }

    public Map<Boolean, Map<String, SysPropertiesInfo>> getIsWorkPropertiesInfoMap()
    {
        return isWorkPropertiesInfoMap;
    }

    public void setIsWorkPropertiesInfoMap(Map<Boolean, Map<String, SysPropertiesInfo>> isWorkPropertiesInfoMap)
    {
        this.isWorkPropertiesInfoMap = isWorkPropertiesInfoMap;
    }
}
