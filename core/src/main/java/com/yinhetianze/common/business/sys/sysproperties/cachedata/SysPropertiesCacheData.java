package com.yinhetianze.common.business.sys.sysproperties.cachedata;

import com.yinhetianze.cachedata.sysproperties.SysPropertiesCacheDataModel;
import com.yinhetianze.cachedata.sysproperties.SysPropertiesInfo;
import com.yinhetianze.common.business.sys.sysproperties.service.SysPropertiesInfoService;
import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统参数配置缓存操作
 */
@Service
public class SysPropertiesCacheData extends RedisCacheData<SysPropertiesCacheDataModel>
{
    @Autowired
    private SysPropertiesInfoService sysPropertiesInfoServiceImpl;

    @Override
    public SysPropertiesCacheDataModel cacheData() throws Exception
    {
        // 最终返回的缓存信息数据格式
        SysPropertiesCacheDataModel sysPropertiesCacheDataModel = new SysPropertiesCacheDataModel();
        List<Map<String, Object>> sysPropertiesList = sysPropertiesInfoServiceImpl.getSysPropertiesInfoList(new HashMap<>());

        // 存放所有系统配置参数的集合，按照id=对象的格式保存
        HashMap<String, SysPropertiesInfo> sysPropertiesInfoMap = new HashMap<>();
        // 存放按照模块配置分类的系统配置参数，按照module=对象格式保存
        HashMap<String, Map<String, SysPropertiesInfo>> moduleSysPropertiesInfo = new HashMap<>();
        // 按照是否删除标记存放的系统参数集合
        HashMap<Boolean, Map<String, SysPropertiesInfo>> delSysPropertiesInfo = new HashMap<>();
        // 按照是否有效的标记存放的系统参数集合
        HashMap<Boolean, Map<String, SysPropertiesInfo>> isWorkSysPropertiesInfo = new HashMap<>();
        if (CommonUtil.isNotEmpty(sysPropertiesList))
        {
            // 临时变量
            SysPropertiesInfo info = null;
            for (Map<String, Object> pojo : sysPropertiesList)
            {
                // 将数据库pojo转成值对象
                info = new SysPropertiesInfo();
                copySysPropertiesInfo(pojo, info);

                // 将值对象保存到所有参数配置集合中
//                sysPropertiesInfoMap.put(info.getId(), info);
                sysPropertiesInfoMap.put(info.getName(), info);
                // 将值对象按照模块保存到模块划分的集合中
                setModuleProperties(info, moduleSysPropertiesInfo);
                // 将值对象按照是否删除保存到对应的集合中
                setSysProperties(info, delSysPropertiesInfo, CommonUtil.oneZeroJudge(info.getDelFlag()));
                // 将值对象按照是否有效保存到对应的集合中
                setSysProperties(info, isWorkSysPropertiesInfo, CommonUtil.oneZeroJudge(info.getIsWork()));

                // 放入到缓存对象中保存数据
                sysPropertiesCacheDataModel.setDelFlagPropertiesInfoMap(delSysPropertiesInfo);
                sysPropertiesCacheDataModel.setIsWorkPropertiesInfoMap(isWorkSysPropertiesInfo);
                sysPropertiesCacheDataModel.setModuleProperitesInfoMap(moduleSysPropertiesInfo);
                sysPropertiesCacheDataModel.setSysPropertiesInfoMap(sysPropertiesInfoMap);
            }
        }

        return sysPropertiesCacheDataModel;
    }

    /**
     * 根据key将info按照id=info的格式保存到对应的子集合中，如果没有子集合，新建并且添加到父集合中
     * @param info
     * @param sysPropertiesInfo
     * @param key
     */
    private void setSysProperties(SysPropertiesInfo info, HashMap<Boolean, Map<String, SysPropertiesInfo>> sysPropertiesInfo, boolean key)
    {
        if (CommonUtil.isNotEmpty(info) && CommonUtil.isNotEmpty(sysPropertiesInfo))
        {
            Map<String, SysPropertiesInfo> keyMap = sysPropertiesInfo.get(key);
            if (CommonUtil.isEmpty(keyMap))
            {
                keyMap = new HashMap<>();
                sysPropertiesInfo.put(key, keyMap);
            }
//            keyMap.put(info.getId(), info);
            keyMap.put(info.getName(), info);
        }
    }

    /**
     * 将info保存到对应的module子集合当中，如果没有子集合，新建并且添加到父集合中
     * @param info
     * @param moduleSysPropertiesInfo
     */
    private void setModuleProperties(SysPropertiesInfo info, HashMap<String, Map<String, SysPropertiesInfo>> moduleSysPropertiesInfo)
    {
        if (CommonUtil.isNotEmpty(info) && CommonUtil.isNotEmpty(moduleSysPropertiesInfo))
        {
            Map<String, SysPropertiesInfo> moduleMap = moduleSysPropertiesInfo.get(info.getModule());
            if (CommonUtil.isEmpty(moduleMap))
            {
                moduleMap = new HashMap<>();
                moduleSysPropertiesInfo.put(info.getModule(), moduleMap);
            }
//            moduleMap.put(info.getId(), info);
            moduleMap.put(info.getName(), info);
        }
    }

    /**
     * 将数据库实体类复制到值对象中
     * @param pojo
     * @param info
     */
    private void copySysPropertiesInfo(Map<String, Object> pojo, SysPropertiesInfo info)
    {
        if (CommonUtil.isNull(pojo) || CommonUtil.isNull(info))
        {
            return ;
        }

        info.setId(String.valueOf(pojo.get("id")));
        info.setName(String.valueOf(pojo.get("name")));
        info.setValue(String.valueOf(pojo.get("value")));
        info.setModule(CommonUtil.isNotEmpty(pojo.get("module")) ? String.valueOf(pojo.get("module")) : null);
        info.setDescription(String.valueOf(pojo.get("description")));
        info.setIsWork(CommonUtil.isNotEmpty(pojo.get("isWork")) ? Integer.valueOf(pojo.get("isWork")+"") : null);
        info.setDelFlag(CommonUtil.isNotEmpty(pojo.get("delFlag")) ? Integer.valueOf(pojo.get("delFlag")+"") : null);
    }
}
