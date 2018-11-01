package com.yinhetianze.common.business.sys.intfpattern.cachedata;

import com.yinhetianze.cachedata.intfpattern.IntfPatternCacheDataModel;
import com.yinhetianze.cachedata.intfpattern.IntfPatternInfo;
import com.yinhetianze.common.business.sys.intfpattern.service.IntfPatternInfoService;
import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IntfPatternCacheData extends RedisCacheData<IntfPatternCacheDataModel>
{
    @Autowired
    private IntfPatternInfoService intfPatternInfoServiceImpl;

    @Override
    public IntfPatternCacheDataModel cacheData() throws Exception
    {
        Collection<Map<String, Object>> intfPatterns = intfPatternInfoServiceImpl.getIntfPattern(new HashMap<>());
        if (CommonUtil.isNotEmpty(intfPatterns))
        {
            // 最终返回的缓存数据
            IntfPatternCacheDataModel model = new IntfPatternCacheDataModel();
            List<IntfPatternInfo> intfPatternList = new ArrayList<>();
            Map<String, IntfPatternInfo> intfPatternMap = new HashMap<>(); // 按照pattern=IntfPatternInfo格式保存
            Map<String, Map<String, IntfPatternInfo>> modulePattern = new HashMap<>(); // 按照模块划分的pattern集合
            Map<String, Map<String, IntfPatternInfo>> permPattern = new HashMap<>(); // 按照权限划分的pattern集合

            IntfPatternInfo info = null; // 临时变量
            for (Map<String, Object> pattern : intfPatterns)
            {
                // 创建pattern值对象
                info = new IntfPatternInfo();
                try
                {
                    // 从pattern的map格式数据转成info对象
                    createInfoPattern(info, pattern);
                    intfPatternList.add(info); // 将info保存到集合中
                    intfPatternMap.put(info.getPattern(), info); // 将info按照pattern=Info的格式保存
                    groupPattern(modulePattern, info); // 按照module分组保存集合
                    groupPattern(permPattern, info); // 按照权限分组保存集合
                }
                catch (Exception e)
                {
                    LoggerUtil.info(IntfPatternCacheData.class, e);
                }
            }

            // 保存到缓存数据中
            model.setIntfPatternList(intfPatternList);
            model.setIntfPatternMap(intfPatternMap);
            model.setModuleIntfPattern(modulePattern);
            model.setPermissionIntfPattern(permPattern);

            return model;
        }

        return null;
    }

    private void groupPattern(Map<String, Map<String, IntfPatternInfo>> groupMap, IntfPatternInfo info)
    {
        if (CommonUtil.isNotEmpty(info) && CommonUtil.isNotEmpty(groupMap))
        {
            if (CommonUtil.isEmpty(groupMap.get(info.getModule())))
            {
                groupMap.put(info.getModule(), new HashMap<>());
            }
            groupMap.get(info.getModule()).put(info.getPattern(), info);
        }
    }

    private IntfPatternInfo createInfoPattern(IntfPatternInfo info, Map<String, Object> pattern)
    {
        if (CommonUtil.isNotEmpty(pattern))
        {
            info.setPattern(CommonUtil.isNotEmpty(pattern.get("pattern"))
                    ? String.valueOf(pattern.get("pattern")) : null);
            info.setModule(CommonUtil.isNotEmpty(pattern.get("module"))
                    ? String.valueOf(pattern.get("module")) : null);
            info.setPermission(CommonUtil.isNotEmpty(pattern.get("permission"))
                    ? String.valueOf(pattern.get("permission")) : null);
            info.setStatus(CommonUtil.isNotEmpty(pattern.get("status"))
                    ? Integer.parseInt(pattern.get("status")+"") : null);
        }
        return null;
    }
}
