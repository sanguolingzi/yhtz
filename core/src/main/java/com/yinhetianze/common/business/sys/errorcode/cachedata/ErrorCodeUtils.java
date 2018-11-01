package com.yinhetianze.common.business.sys.errorcode.cachedata;

import com.yinhetianze.cachedata.errorcode.ErrorCodeCacheDataModel;
import com.yinhetianze.cachedata.errorcode.ErrorCodeInfo;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ErrorCodeUtils
{
    @Autowired
    private ErrorCodeCacheData cacheData;

    /**
     * 获取缓存的errocodemodel
     * @return
     */
    private ErrorCodeCacheDataModel getCacheData()
    {
        if (CommonUtil.isNotEmpty(cacheData))
        {
            ErrorCodeCacheDataModel model = cacheData.getCacheData();
            if (CommonUtil.isNotEmpty(model))
            {
                return model;
            }
            else
            {
                LoggerUtil.info(ErrorCodeUtils.class, "没有缓存信息：{}", new Object[]{cacheData.getCacheName()});
            }
        }
        return null;
    }

    /**
     * 根据状态值true/false获取对应的所有错误编码集合
     * @param status
     * @return
     */
    public final Map<String, ErrorCodeInfo> getErrorCodeMapByStatus(Boolean status)
    {
        ErrorCodeCacheDataModel model = getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            Map<Boolean, Map<String, ErrorCodeInfo>> moduleMap = model.getStatusErrorCodeMap();
            if (CommonUtil.isNotEmpty(moduleMap))
            {
                return moduleMap.get(status);
            }
        }
        return null;
    }

    /**
     * 根据module类型获取该类型的所有错误编码集合
     * @param module
     * @return
     */
    public final Map<String, ErrorCodeInfo> getErrorCodeMapByModule(String module)
    {
        ErrorCodeCacheDataModel model = getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            Map<String, Map<String, ErrorCodeInfo>> moduleMap = model.getTypeErrorCodeMap();
            if (CommonUtil.isNotEmpty(moduleMap))
            {
                return moduleMap.get(module);
            }
        }
        return null;
    }

    /**
     * 获取所有错误编码信息列表
     * @return
     */
    public final List<ErrorCodeInfo> getErrorCodeList()
    {
        ErrorCodeCacheDataModel model = getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            return model.getErrorCodeList();
        }

        return null;
    }

    /**
     * 根据错误信息编码获取错误信息
     * @param errorCode
     * @return
     */
    public String getErrorMessage(String errorCode)
    {
        ErrorCodeCacheDataModel model = getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            Map<String, ErrorCodeInfo> errorCodeInfoMap = model.getErrorCodeMap();
            if (CommonUtil.isNotEmpty(errorCodeInfoMap))
            {
                ErrorCodeInfo info = errorCodeInfoMap.get(errorCode);
                if (CommonUtil.isNotEmpty(info))
                {
                    return info.getErrorMessage();
                }
            }
        }

        return null;
    }

    /**
     * 根据错误信息编码获取错误信息对象
     * @param errorCode
     * @return
     */
    public final ErrorCodeInfo getErrorCodeInfo(String errorCode)
    {
        ErrorCodeCacheDataModel model = getCacheData();
        if (CommonUtil.isNotEmpty(model))
        {
            Map<String, ErrorCodeInfo> errorCodeInfoMap = model.getErrorCodeMap();
            if (CommonUtil.isNotEmpty(errorCodeInfoMap))
            {
                return errorCodeInfoMap.get(errorCode);
            }
        }

        return null;
    }
}
