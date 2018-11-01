package com.yinhetianze.common.business.sys.errorcode.cachedata;

import com.yinhetianze.cachedata.errorcode.ErrorCodeCacheDataModel;
import com.yinhetianze.cachedata.errorcode.ErrorCodeInfo;
import com.yinhetianze.common.business.sys.errorcode.service.ErrorCodeInfoService;
import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ErrorCodeCacheData extends RedisCacheData<ErrorCodeCacheDataModel>
{
    @Autowired
    private ErrorCodeInfoService commonErrorCodeInfoServiceImpl;

    @Override
    public ErrorCodeCacheDataModel cacheData() throws Exception
    {
        List<Map<String, Object>> errorCodeList = commonErrorCodeInfoServiceImpl.getErrorCodeMap(new HashMap<>());
        if (CommonUtil.isNotEmpty(errorCodeList))
        {
            ErrorCodeCacheDataModel model = new ErrorCodeCacheDataModel(); // 缓存数据格式
            Map<String, ErrorCodeInfo> errorCodeInfoMap = new HashMap<>(); // 所有错误信息集合
            List<ErrorCodeInfo> errorCodeInfoList = new ArrayList<>(); // 所有错误信息列表
            Map<String, Map<String, ErrorCodeInfo>> moduleErrorCodeMap = new HashMap<>(); // 模块分组集合
            Map<Boolean, Map<String, ErrorCodeInfo>> statusErrorCodeMap = new HashMap<>(); // 状态分组集合

            ErrorCodeInfo info = null; // 临时变量
            for (Map<String, Object> ec : errorCodeList)
            {
                info = new ErrorCodeInfo();
                createErrorCodeInfo(ec, info);

                // 将info保存到各分组中
                addErrorCodeInfo(info, errorCodeInfoMap);
                errorCodeInfoList.add(info);
                addErrorCodeinfo(info, moduleErrorCodeMap, info.getType());
                addErrorCodeinfo(info, statusErrorCodeMap, CommonUtil.oneZeroJudge(info.getStatus()));
            }

            // 将个分组信息保存到缓存model中
            model.setErrorCodeList(errorCodeInfoList);
            model.setErrorCodeMap(errorCodeInfoMap);
            model.setStatusErrorCodeMap(statusErrorCodeMap);
            model.setTypeErrorCodeMap(moduleErrorCodeMap);
            return model;
        }
        return null;
    }

    /**
     * 根据指定的obj分组信息以code=ErrorCodeInfo格式保存到errorCodeMap中
     * @param info
     * @param errorCodeMap
     * @param obj
     */
    private void addErrorCodeinfo(ErrorCodeInfo info, Map errorCodeMap, Object obj)
    {
        if (CommonUtil.isNotEmpty(info) && CommonUtil.isNotNull(errorCodeMap))
        {
            Map infoMap = (Map) errorCodeMap.get(obj);
            if (CommonUtil.isNull(infoMap))
            {
                infoMap = new HashMap<>();
                errorCodeMap.put(info.getType(), infoMap);
            }
            infoMap.put(info.getErrorCode(), info);
        }
    }

    /**
     * 将info以code=ErrorCodeInfo的格式保存到errorCodeInfoMap中
     * @param info
     * @param errorCodeInfoMap
     */
    private void addErrorCodeInfo(ErrorCodeInfo info, Map<String, ErrorCodeInfo> errorCodeInfoMap)
    {
        if (CommonUtil.isNotEmpty(info) && CommonUtil.isNotNull(errorCodeInfoMap))
        {
            errorCodeInfoMap.put(info.getErrorCode(), info);
        }
    }

    /**
     * 将map格式的数据转换成ErrorCodeInfo
     * @param ec
     * @param info
     * @return
     */
    private void createErrorCodeInfo(Map<String, Object> ec, ErrorCodeInfo info)
    {
        if (CommonUtil.isNotEmpty(ec) && CommonUtil.isNotEmpty(info))
        {
            info.setErrorCode(String.valueOf(ec.get("errorCode")));
            info.setErrorMessage(String.valueOf(ec.get("errorMessage")));
            info.setBusinessCode(CommonUtil.isNotNull(ec.get("businessCode")) ? ec.get("businessCode")+"" : null);
            info.setRemarks(CommonUtil.isNotNull(ec.get("remarks")) ? ec.get("remarks")+"" : null);
            info.setStatus(CommonUtil.isNotEmpty(ec.get("status")) ? Integer.parseInt(ec.get("status")+"") : null);
            info.setType(CommonUtil.isNotNull(ec.get("type")) ? String.valueOf(ec.get("type")) : null);
            info.setSortNumber(CommonUtil.isNotEmpty(ec.get("sortNumber")) ? Integer.valueOf(ec.get("sortNumber")+"") : null);
            info.setCreateTime(CommonUtil.isNotEmpty(ec.get("createTime")) ? (Date) ec.get("createTime") : null);

        }
    }
}
