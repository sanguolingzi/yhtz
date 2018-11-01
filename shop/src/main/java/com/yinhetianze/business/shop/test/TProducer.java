package com.yinhetianze.business.shop.test;

//import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

//@Component
public class TProducer {

    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsTemplate customerJmsTemplate;
    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage(Destination destination, final String message){
        //jmsTemplate.setDeliveryMode();
        customerJmsTemplate.setSessionAcknowledgeMode(2);
        customerJmsTemplate.setSessionTransacted(false);
        customerJmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(message);
                textMessage.setStringProperty("luoxiang","asd");
                //textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5000);
                return textMessage;
            }
        });
        //jmsTemplate.convertAndSend(destination, message);
    }
}
