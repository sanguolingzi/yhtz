package com.yinhetianze.back.user;

import com.yinhetianze.business.shop.service.busi.ShopProxyBusiService;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("back")
public class ScanNotifyController extends RestfulController {

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private ShopProxyBusiService shopProxyBusiServiceImpl;

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Autowired
    private WxService wxMpService;

    //扫码事件回调
    @RequestMapping("/getEvent")
    public void getEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try{
            //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
            String signature = request.getParameter("signature");
            //时间戳
            String timestamp = request.getParameter("timestamp");
            //随机数
            String nonce = request.getParameter("nonce");
            //随机字符串
            String echostr = request.getParameter("echostr");

            if (wxMpService.checkSignature( timestamp, nonce,signature)) {
                InputStream inStream = request.getInputStream();
                ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outSteam.write(buffer, 0, len);
                }
                outSteam.close();
                inStream.close();
                String result = new String(outSteam.toByteArray(), "utf-8");
                //存放微信返回信息的map
                Map<String, String> requestMap = new HashMap<>();
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                documentBuilderFactory.setExpandEntityReferences(false);
                documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
                InputStream stream = new ByteArrayInputStream(result.getBytes("UTF-8"));
                Document doc = documentBuilder.parse(stream);
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getDocumentElement().getChildNodes();
                for (int idx=0; idx<nodeList.getLength(); ++idx) {
                    Node node = nodeList.item(idx);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (org.w3c.dom.Element) node;
                        requestMap.put(element.getNodeName(), element.getTextContent());
                    }
                }
                stream.close();

                // 发送方帐号（open_id）
                String fromUserName = requestMap.get("FromUserName");
                // 公众帐号
                String toUserName = requestMap.get("ToUserName");
                // 消息类型
                String msgType = requestMap.get("MsgType");
                //事件类型
                String eventType = requestMap.get("Event");
                //二维码参数值
                String eventKey = requestMap.get("EventKey");

                String key="";
                if(msgType.equals("event")){
                    ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
                    ShopProxyPojo shopProxyPojo1=new ShopProxyPojo();
                    //首次关注,取参数值
                    if(eventType.equals("subscribe")||eventType.equals("SCAN")){
                        if(eventType.equals("subscribe")){
                            key=eventKey.split("qrscene_")[1];
                        }else{      //已关注过
                            key=eventKey;
                        }
                        shopProxyPojo.setShopPhone(key);
                        shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
                        shopProxyPojo1.setOpenId(fromUserName);
                        shopProxyPojo1.setId(shopProxyPojo.getId());
                        shopProxyBusiServiceImpl.updateByPrimaryKeySelective(shopProxyPojo1);
                        //将参数值存入redis中
                        redisManager.setSerializeObject(key,key,5*60 * 1000L);
                        out.print("<xml><ToUserName><![CDATA["+fromUserName+"]]></ToUserName><FromUserName><![CDATA["+toUserName+"]]></FromUserName><CreateTime>"+new Date().getTime()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好,欢迎关注商城公众号。]]></Content></xml>");
                        //取消关注
                    }else if(eventType.equals("unsubscribe")){
                        shopProxyPojo.setOpenId(fromUserName);
                        shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
                        shopProxyPojo1.setId(shopProxyPojo.getId());
                        shopProxyPojo1.setOpenId("");
                        shopProxyBusiServiceImpl.updateByPrimaryKeySelective(shopProxyPojo1);
                        out.print(echostr);
                    }
                }else{
                    out.print(echostr);
                }
            }else{
                out.print(echostr);
            }
        }catch (Exception e){
            LoggerUtil.error(BackUserController.class,e);
        }finally {
            out.close();
        }
    }
}
