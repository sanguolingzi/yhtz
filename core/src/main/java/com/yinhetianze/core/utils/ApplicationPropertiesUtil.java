package com.yinhetianze.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * springcloud读取配置文件内容的工具类
 * 配置中心更新后的环境配置信息
 * 通过此类注入来获取string，integer，long，bigdecimal类型的配置
 */
public class ApplicationPropertiesUtil
{
    /**
     * springboot的环境配置对象
     * 通过该对象获取环境配置信息
     */
    @Autowired
    private Environment environment;

    public String getStrProp(String key, String defaultValue)
    {
        if (CommonUtil.isNotEmpty(environment))
        {
            return environment.getProperty(key, defaultValue);
        }
        else
        {
            return defaultValue;
        }
    }

    /**
     * 获取字符串格式的配置值
     * @param key
     * @return
     */
    public String getStrProp(String key)
    {
        return getStrProp(key, "");
    }

    /**
     * 获取整数型配置值，默认返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public Integer getIntProp(String key, Integer defaultValue)
    {
        if (CommonUtil.isNotEmpty(environment))
        {
            try
            {
                return CommonUtil.isNotEmpty(environment.getProperty(key))
                        ? Integer.parseInt(environment.getProperty(key)) : defaultValue;
            }
            catch (Exception e)
            {
                LoggerUtil.error(ApplicationPropertiesUtil.class, e);
            }
        }

        return defaultValue;
    }

    /**
     * 获取整数型配置值
     * @param key
     * @return
     */
    public Integer getIntProp(String key)
    {
        return getIntProp(key, null);
    }

    /**
     * 获取大数字配置值，默认返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public BigDecimal getBigDecimalProp(String key, BigDecimal defaultValue)
    {
        if (CommonUtil.isNotEmpty(environment))
        {
            try
            {
                return CommonUtil.isNotEmpty(environment.getProperty(key))
                        ? new BigDecimal(environment.getProperty(key)) : defaultValue;
            }
            catch (Exception e)
            {
                LoggerUtil.error(ApplicationPropertiesUtil.class, e);
            }
        }

        return defaultValue;
    }

    /**
     * 获取大数字格式的配置值
     * @param key
     * @return
     */
    public BigDecimal getBigDecimalProp(String key)
    {
        return getBigDecimalProp(key, null);
    }

    /**
     * 获取长整形配置值，默认返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public Long getLongProp(String key, Long defaultValue)
    {
        if (CommonUtil.isNotEmpty(environment))
        {
            try
            {
                return CommonUtil.isNotEmpty(environment.getProperty(key))
                        ? Long.parseLong(environment.getProperty(key)) : defaultValue;
            }
            catch (Exception e)
            {
                LoggerUtil.error(ApplicationPropertiesUtil.class, e);
            }
        }

        return defaultValue;
    }

    /**
     * 获取长整形配置值
     * @param key
     * @return
     */
    public Long getLongProp(String key)
    {
        return getLongProp(key, null);
    }

    /**
     * 获取map型key-value格式的键值对配置值
     * 集合多配置以逗号隔开
     * @param key
     * @return
     */
    public Map<String, String> getMapProp(String key)
    {
        // 结果集合
        Map<String, String> result = new HashMap<>();

        if (CommonUtil.isNotEmpty(environment))
        {
            // map字符串配置
            String mapStr = environment.getProperty(key);
            if (CommonUtil.isNotEmpty(mapStr))
            {
                // 将字符串通过逗号分隔
                String[] entities = mapStr.split(",");
                String[] temp = null;
                for (String en : entities)
                {
                    // 如果不是以等号配置的key=value格式视为无效配置
                    temp = en.trim().split("=");
                    if (temp.length == 2)
                    {
                        result.put(CommonUtil.isNotEmpty(temp[0]) ? temp[0].trim() : temp[0]
                                , CommonUtil.isNotEmpty(temp[1]) ? temp[1].trim() : temp[1]);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 获取boolean配置值，获取不到配置或者配置为空事返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public Boolean getBoolProp(String key, Boolean defaultValue)
    {
        if (CommonUtil.isNotEmpty(environment))
        {
            try
            {
                return CommonUtil.isNotEmpty(environment.getProperty(key))
                        ? Boolean.parseBoolean(environment.getProperty(key)) : defaultValue;
            }
            catch (Exception e)
            {
                LoggerUtil.error(ApplicationPropertiesUtil.class, e);
            }
        }

        return defaultValue;
    }

    /**
     * 获取boolean型配置值
     * @param key
     * @return
     */
    public Boolean getBoolProp(String key)
    {
        return getBoolProp(key, null);
    }

    /**
     * 获取集合类型的配置，配置的元数据以逗号隔开，返回的集合类型为指定泛型类型
     * @param key 配置的键
     * @param col 返回的配置的集合类型
     * @return
     */
    public <T extends Collection> T getCollectionProp(String key, T col)
    {
        if (CommonUtil.isNotEmpty(environment) && CommonUtil.isNotEmpty(col))
        {
            if (CommonUtil.isNotEmpty(key))
            {
                String valueStr = environment.getProperty(key);
                if (CommonUtil.isNotEmpty(valueStr))
                {
                    String[] values = valueStr.split(",");
                    for (String v : values)
                    {
                        col.add(v.trim());
                    }
                }
            }
        }

        return col;
    }

}
