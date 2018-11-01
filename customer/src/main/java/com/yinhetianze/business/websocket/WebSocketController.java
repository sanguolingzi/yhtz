package com.yinhetianze.business.websocket;

import com.yinhetianze.configurations.components.ClientMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//@Controller
//@RequestMapping("/socket")
public class WebSocketController {
/*
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendTest")
    @SendTo("/topic/subscribeTest")
    public String sendDemo(ClientMessage message) {
        System.out.println("接收到了信息" + message.getName());
        return "你发送的消息为:" + message.getName();
    }

    @SubscribeMapping("/subscribeTest")
    public String sub() {
        System.out.println("XXX用户订阅了我。。。");
        return "感谢你订阅了我。。。";
    }



    //客户端只要订阅了/topic/subscribeTest主题，调用这个方法即可
    public void templateTest() {
        messagingTemplate.convertAndSend("/topic/subscribeTest", "服务器主动推的数据");
    }
    */
}
