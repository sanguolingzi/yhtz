package com.yinhetianze.core.cachedata;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * redis缓存数据抽象父类，主要功能如下
 * 1、从redis中获取缓存数据
 * 2、从数据源获取数据后缓存到redis
 * 3、当redis不能正常工作时将数据保存到临时静态缓存中（未实现）
 * 4、当redis恢复工作时将临时静态缓存中的数据迁移到redis中并清空临时缓存数据（未实现）
 * @param <T>
 */
public abstract class RedisCacheData<T extends Serializable> extends AbstractCacheData<T>
{

    /**
     * 临时保存静态数据
     * 当redis不能正常工作时使用
     */
    private static Map<String, Object> staticCacheDataMap = new HashMap<>();

    /**
     * redis管理类
     * 调用底层redis方法
     */
    @Autowired
    private RedisManager redisManager;

    @Override
    public T getCacheData()
    {
        // 从redis中获取缓存数据
        Serializable data = redisManager.getSerializeObject(getCacheName(), Serializable.class);
//        redisManager.setSerializeObject(cacheName, this.getClass().getGenericSuperclass().getClass());

        // 如果数据不为空，则返回，如果数据为空，则从数据源获取数据并存放到缓存
        if (CommonUtil.isNotEmpty(data))
        {
            // 返回缓存数据
            return (T) data;
        }
        else
        {
            try
            {
                // 从数据源获取数据
                data = cacheData();
                if (CommonUtil.isNotEmpty(data))
                {
                    // 保存到redis中
                    handleData((T) data);
                    return (T) data;
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(RedisCacheData.class, e);
            }
        }

        return null;
    }

    @Override
    public final void handleData(T data)
    {
        if (CommonUtil.isNotEmpty(data))
        {
            redisManager.setSerializeObject(getCacheName(), data);
        }
    }
}
