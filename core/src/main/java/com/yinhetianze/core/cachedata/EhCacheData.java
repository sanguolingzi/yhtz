package com.yinhetianze.core.cachedata;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用ehcache作为缓存主要功能如下
 * 1、将数据存放到ehcache的cacheName中
 * 2、从ehcache中获取缓存数据
 * 预制条件：必须存在ehcache.xml配置文件，配置文件中必须有名为：【busiCache】的cache对象配置
 * @param <T>
 */
public abstract class EhCacheData<T> extends AbstractCacheData<T>
{
    /**
     * 需要存放到ehcache中的哪一个缓存中
     */
    protected String ehcacheName;

    /**
     * 系统设置的存放数据的ehcache缓存对象名称
     */
    private static final String EHCACHE_NAME = "busiCache";

    /**
     * 静态缓存对象
     * 当ehcache不能工作时使用
     */
    private static Map<String, Object> statiCache = new HashMap<>();

    @Override
    public void handleData(T data)
    {
        // 数据不为空时存放到缓存中
        if (CommonUtil.isNotEmpty(data))
        {
            // ehcache缓存对象
            CacheManager cacheManager = CacheManager.getInstance();
            Cache cache = cacheManager.getCache(EHCACHE_NAME);

            // 存放到缓存对象中，数据以element数据格式保存
            if (CommonUtil.isNotEmpty(cache))
            {
                Element e = new Element(getCacheName(), data);
                cache.put(e);
            }
            else
            {
                // cache不存在时存放到静态缓存中
                statiCache.put(getCacheName(), data);
            }
        }
    }

    @Override
    public T getCacheData()
    {
        // ehcache缓存对象
        CacheManager manager = CacheManager.getInstance();
        Cache cache = manager.getCache(EHCACHE_NAME);

        // 对象不为空时进行操作
        if (CommonUtil.isNotEmpty(cache))
        {
            // 缓存数据
            Object data = cache.get(getCacheName());
            if (CommonUtil.isNotEmpty(data))
            {
                // 不为空时返回
                Element e = (Element)data;
                return (T)e.getObjectValue();
            }
            else
            {
                try
                {
                    // 为空时从数据源获取缓存数据
                    data = cacheData();
                    if (CommonUtil.isNotEmpty(data))
                    {
                        // 获取导数据则放入缓存中
                        Element e = new Element(getCacheName(), data);
                        cache.put(e);
                        return (T) data;
                    }
                }
                catch (Exception e)
                {
                    LoggerUtil.error(EhCacheData.class, e);
                }
            }
        }

        // 为空或异常时直接从静态缓存中获取数据
        return (T) statiCache.get(getCacheName());
    }
}
