package com.yinhetianze.core.cachedata;

/**
 * 刷新缓存接口
 * @author Administrator
 *
 */
public interface CacheData<T>
{
    
    /**
     * 刷新缓存
     */
    T refreshCache();
    
    /**
     * 获取缓存中的数据
     * @return
     */
    T getCacheData();

    /**
     * 获取缓存存储的名称
     * @return
     */
    String getCacheName();
    
}
