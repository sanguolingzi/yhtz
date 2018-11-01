package com.yinhetianze.core.utils;

/**
 * 断言处理
 */
public class Asserts
{
    /**
     * 对象不能为空
     * @param obj
     */
    public static void notEmpty(Object obj)
    {
        notEmpty(obj, "对象不能为空");
    }

    /**
     * 对象不能为空，指定提示信息
     * @param obj
     * @param message
     */
    public static void notEmpty(Object obj, String message)
    {
        if (CommonUtil.isNull(obj))
        {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 空串或者空白字符串
     * @param content
     */
    public static void notBlank(String content)
    {
        notBlank(content, "不能为空白字符或者空串");
    }

    /**
     * 空串或者空白字符串，提示指定信息
     * @param content
     * @param message
     */
    public static void notBlank(String content, String message)
    {
        if (CommonUtil.isEmpty(content) || CommonUtil.isEmpty(content.trim()))
        {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 判断是否是true，如果不是true，返回指定的错误信息
     * @param result
     * @param message
     */
    public static void isTrue(Boolean result, String message)
    {
        if (!result)
        {
            throw new IllegalArgumentException(message);
        }
    }


}
