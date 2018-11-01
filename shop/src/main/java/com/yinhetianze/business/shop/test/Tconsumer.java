package com.yinhetianze.business.shop.test;

import com.yinhetianze.business.shop.service.busi.ShopBusiService;
import com.yinhetianze.core.business.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Session;

import javax.jms.TextMessage;
import java.util.Random;

@Component
@Transactional(rollbackFor = Exception.class)
public class Tconsumer {


    @Autowired
    private ShopBusiService shopBusiServiceImpl;


    @JmsListener(destination = "mytest.queue",selector = "luoxiang='asd'")
    public void receiveQueue(final TextMessage text, Session session)  throws Exception {
        try {

            System.out.println("消息签收模式:"+  session.getTransacted());
            System.out.println("消息签收模式:"+session.getAcknowledgeMode());
            System.out.println("Consumer收到的报文为:"+text.getText());
            //text.acknowledge();// 使用手动签收模式，需要手动的调用，如果不在catch中调用session.recover()消息只会在重启服务后重发
            //session.recover();
            //int i = new Random().nextInt(4);
            //if(i%2!=0){
                //System.out.println(1/0);
            //}
            //if(true){
                //throw new BusinessException("123");
            //}

        } catch (Exception e) {
           //session.recover();// 此不可省略 重发信息使用
            //throw new JMSException("1234556");
            session.rollback();
            throw e;
        }
    }
}
