package com.yinhetianze.core.cachedata;

import com.yinhetianze.core.utils.LoggerUtil;

/**
 * 数据缓存的抽象模板类
 * 固定化处理缓存数据的产生与存储
 * @param <T>
 */
public abstract class AbstractCacheData<T> implements CacheData<T>
{
    /**
     * 缓存名称自定义拓展名
     * 拼接在缓存名称后面
     */
    private static final String CACHE_EXT = "_CACHEDATA";

    /**
     * 缓存名称
     */
    protected String cacheName;

    /**
     * 初始化缓存名称
     */
    public AbstractCacheData()
    {
        this.cacheName = this.getClass().getName() + CACHE_EXT;
    }

    /**
     * 固定化缓存名称的获取方式
     * @return
     */
    @Override
    public final String getCacheName()
    {
        return cacheName;
    }

    /**
     * 模板方法
     * 1、获取缓存数据
     * 2、将缓存数据保存到对应的缓存区域
     * @return
     */
    @Override
    public final T refreshCache()
    {
        try
        {
            // 获取缓存数据
            T obj = cacheData();

            // 保存缓存数据
            handleData(obj);

            return obj;
        }
        catch (Exception e)
        {
            LoggerUtil.error(AbstractCacheData.class, e.toString());
            return null;
        }
    }

    /**
     * 子类必须实现的自定义从数据来源获取需要被缓存的数据
     * @return
     */
    public abstract T cacheData() throws Exception;

    /**
     * 存放缓存数据到对应的缓存区域
     * @param data
     */
    public abstract void handleData(T data);
}
