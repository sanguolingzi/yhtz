package com.yinhetianze.core.cachedata;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态缓存数据
 * 从内存中获取相应的缓存数据
 * 内存中的数据保存在java.util.Map中
 * 固定化获取静态缓存的方法
 */
public abstract class StaticCacheData<T> extends AbstractCacheData<T>
{
    /**
     * 静态缓存数据存储对象
     */
    private static Map<String, Object> cacheData = new HashMap<>();

    @Override
    public final void handleData(Object data)
    {
        if (CommonUtil.isNotEmpty(data))
        {
            cacheData.put(getCacheName(), data);
        }
    }

    @Override
    public final T getCacheData()
    {
        // 缓存名称
        String cacheName = getCacheName();

        // 根据缓存名称获取缓存数据
        Object data = cacheData.get(cacheName);

        // 如果缓存数据存在，则返回，否则从数据源获取数据再存放到缓存中
        if (CommonUtil.isNotEmpty(data))
        {
            // 返回缓存数据
            return (T) data;
        }
        else
        {
            try
            {
                // 从数据源获取缓存数据
                data = cacheData();
                if (CommonUtil.isNotEmpty(data))
                {
                    // 将数据保存到缓存中然后返回
                    handleData(data);
                    return (T) data;
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(StaticCacheData.class, e);
            }
        }

        // 没有缓存数据返回空
        return null;
    }
}
