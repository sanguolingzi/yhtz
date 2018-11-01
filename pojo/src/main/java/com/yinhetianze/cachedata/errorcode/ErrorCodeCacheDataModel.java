package com.yinhetianze.cachedata.errorcode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 缓存对象
 */
public class ErrorCodeCacheDataModel implements Serializable
{
    /**
     * 所有错误编码集合，按照code=ErrorCodeInfo的格式存储
     */
    private Map<String, ErrorCodeInfo> errorCodeMap;

    /**
     * 所有错误编码集合，独立存储ErrorCodeInfo
     */
    private List<ErrorCodeInfo> errorCodeList;

    /**
     * 按照type分组，组内每个errorinfo以code=ErrorCodeInfo的格式存储
     */
    private Map<String, Map<String, ErrorCodeInfo>> typeErrorCodeMap;

    /**
     * 按照errorInfo的有效状态标志分组，组内以code=ErrorCodeInfo的格式存储
     */
    private Map<Boolean, Map<String, ErrorCodeInfo>> statusErrorCodeMap;

    public Map<String, ErrorCodeInfo> getErrorCodeMap()
    {
        return errorCodeMap;
    }

    public void setErrorCodeMap(Map<String, ErrorCodeInfo> errorCodeMap)
    {
        this.errorCodeMap = errorCodeMap;
    }

    public List<ErrorCodeInfo> getErrorCodeList()
    {
        return errorCodeList;
    }

    public void setErrorCodeList(List<ErrorCodeInfo> errorCodeList)
    {
        this.errorCodeList = errorCodeList;
    }

    public Map<String, Map<String, ErrorCodeInfo>> getTypeErrorCodeMap()
    {
        return typeErrorCodeMap;
    }

    public void setTypeErrorCodeMap(Map<String, Map<String, ErrorCodeInfo>> typeErrorCodeMap)
    {
        this.typeErrorCodeMap = typeErrorCodeMap;
    }

    public Map<Boolean, Map<String, ErrorCodeInfo>> getStatusErrorCodeMap()
    {
        return statusErrorCodeMap;
    }

    public void setStatusErrorCodeMap(Map<Boolean, Map<String, ErrorCodeInfo>> statusErrorCodeMap)
    {
        this.statusErrorCodeMap = statusErrorCodeMap;
    }
}
