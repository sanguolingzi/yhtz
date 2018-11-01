package com.yinhetianze.jms.activemq;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;

/**
 * 消息实体工厂类
 * 提供获取消息实体类的方法
 * @author Administrator
 *
 */
public class AmqMessageFactory
{
    
    /**
     * 获得原始话题消息实体类
     * 需要自己指定发送消息目的地destinationName
     * @return
     */
    public static AmqMessage getTopicMessage()
    {
        return new AmqTopicMessage();
    }

    /**
     * 获得原始队列消息实体类
     * 需要自己指定发送消息目的地destinationName
     * @return
     */
    public static AmqMessage getQueueMessage()
    {
        return new AmqQueueMessage();
    }

    /**
     * 获得原始话题消息实体类
     * @return
     */
    public static AmqMessage getTopicMessage(String destinationName)
    {
        return new AmqTopicMessage(destinationName);
    }

    /**
     * 获得原始队列消息实体类
     * @return
     */
    public static AmqMessage getQueueMessage(String destinationName)
    {
        return new AmqQueueMessage(destinationName);
    }
    
    /**
     * 通过指定参数获取相对应的消息实体类
     * @param destination 消息目的地(队列/话题名称)
     * @param isPubSubDomain 订阅/队列类型，true订阅类型消息实体，false队列类型消息实体
     * @return
     */
    public static AmqMessage getAmqMessage(String destination, boolean isPubSubDomain)
    {
        return isPubSubDomain ? new AmqTopicMessage(destination) : new AmqQueueMessage(destination);
    }
    
}
