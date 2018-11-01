package com.yinhetianze.business.shop.test;

//import org.apache.activemq.broker.jmx.BrokerViewMBean;
//import org.apache.activemq.broker.jmx.QueueViewMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestGetMqInfo {
/*
    public static void main(String[] args){

        getAllQueueSize();


    }





    public static Map<String,Long> getAllQueueSize() {
        Map<String,Long> queueMap=new HashMap<String, Long>();
        BrokerViewMBean mBean=null;
        MBeanServerConnection connection=null;
        try{
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
            JMXConnector connector = JMXConnectorFactory.connect(url);
            connector.connect();
            connection = connector.getMBeanServerConnection();
            ObjectName name = new ObjectName("org.apache.activemq:brokerName=localhost,type=Broker");
            mBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
        }catch (IOException e){
            e.printStackTrace();
        }catch (MalformedObjectNameException e){
            e.printStackTrace();
        }
        System.out.println("-----start-------");
        if(mBean!=null){
            System.out.println("-----mBean.getQueues()-------"+mBean.getQueues().length);
            for (ObjectName queueName : mBean.getQueues()) {
                QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);
                queueMap.put(queueMBean.getName(), queueMBean.getQueueSize());
                System.out.println("Queue Name --- " + queueMBean.getName());// 消息队列名称
                System.out.println("Queue Size --- " + queueMBean.getQueueSize());// 队列中剩余的消息数
                System.out.println("Number of Consumers --- " + queueMBean.getConsumerCount());// 消费者数
                System.out.println("Number of Dequeue ---" + queueMBean.getDequeueCount());// 出队数
            }
        }
        System.out.println("-----end-------");

        return queueMap;
    }
    */
}
