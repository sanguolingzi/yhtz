package com.yinhetianze.business.order.controller;


import com.alipay.api.internal.util.AlipaySignature;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.util.SignUtils;
import com.yinhetianze.business.alipay.AlipayConfig;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("order")
public class PayController extends RestfulController {

    @Autowired
    private WxPayConfig payConfig;

    @Autowired
    private WxPayConfig appPayConfig;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @RequestMapping("/wxPay")
    public void getJSSDKCallbackData(HttpServletRequest request, HttpServletResponse response) throws BusinessException{
        //微信支付回调接口
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
            Map<String, String> kvm = xmlToMap(result);
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );

            Map<String,Object> transaction=new HashMap<String,Object>();
            transaction.put("type", 2);
            transaction.put("dealNo", kvm.get("transaction_id"));
            transaction.put("totalOrdersNo", kvm.get("out_trade_no"));
            transaction.put("transResult", kvm.get("return_code"));
            transaction.put("returnMsg", kvm.get("return_msg"));
            transaction.put("appid", kvm.get("appid"));
            transaction.put("amount", kvm.get("total_fee"));
            transaction.put("tradeTime", sdf.parse(kvm.get("time_end")));
            //查询记录表（没有记录加一条，有记录并且返回的是fail，更新记录）
            Map<String,Object> m=orderInfoServiceImpl.findOrderTransRecord(kvm.get("out_trade_no"),2);
            if(CommonUtil.isNotEmpty(m)){
                if(m.get("transResult").equals("FAIL")){
                    transaction.put("id", m.get("id"));
                    orderBusiServiceImpl.updateTransRecord(transaction);
                }
            }else{
                transaction.put("createTime", new Date());
                orderBusiServiceImpl.addOrderTransRecord(transaction);
            }

            if (kvm.get("result_code").equals("SUCCESS")) {
                Map<String,Object> orderParment=new HashMap<>();
                orderParment.put("totalOrderNo",kvm.get("out_trade_no"));
                List<Map<String,Object>> list=orderInfoServiceImpl.findOrder(orderParment);
                if(CommonUtil.isEmpty(list)){
                    throw new BusinessException("订单号异常");
                }
                //验证签名
                Boolean falg=false;
                if(Integer.valueOf(list.get(0).get("orderSourse")+"")==3){
                    falg=SignUtils.checkSign(kvm, null, this.appPayConfig.getMchKey());
                }else{
                    falg=SignUtils.checkSign(kvm, null, this.payConfig.getMchKey());
                }
                if (!falg){
                    response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[check signature FAIL]]></return_msg></xml>");
                }
                //如果订单状态为1，说明还没有付款，可以去更新订单状态
                if(Integer.valueOf(list.get(0).get("orderStatus")+"")==1){
                    orderBusiServiceImpl.payOrder(list,2);
                }
                response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[ok]]></return_msg></xml>");
            }else{
                response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[result_code is FAIL]]></return_msg></xml>");
            }

            /*if (SignUtils.checkSign(kvm, null, this.payConfig.getMchKey())){
                if (kvm.get("result_code").equals("SUCCESS")) {
                    Map<String,Object> orderParment=new HashMap<>();
                    orderParment.put("totalOrderNo",kvm.get("out_trade_no"));
                    List<Map<String,Object>> list=orderInfoServiceImpl.findOrder(orderParment);
                    if(CommonUtil.isEmpty(list)){
                        throw new BusinessException("订单号异常");
                    }
                    //如果订单状态为1，说明还没有付款，可以去更新订单状态
                    if(Integer.valueOf(list.get(0).get("orderStatus")+"")==1){
                        orderBusiServiceImpl.payOrder(list,2);
                    }
                    response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[ok]]></return_msg></xml>");
                }else{
                    response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[result_code is FAIL]]></return_msg></xml>");
                }
            }else{
                response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[check signature FAIL]]></return_msg></xml>");
            }*/
        } catch (Exception e) {
            LoggerUtil.error(PayController.class,e);
            throw new BusinessException("微信支付失败");
        }
    }


    @RequestMapping("/aliPay")
    public String notify(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
        LoggerUtil.error(PayController.class,"params:"+params);
        try {
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            Map<String,Object> transaction=new HashMap<String,Object>();
            transaction.put("type", 1);
            transaction.put("dealNo", tradeNo);
            transaction.put("totalOrdersNo",outTradeNo);
            transaction.put("transResult", tradeStatus);
            //transaction.put("returnMsg", params.get("return_msg"));
            transaction.put("appid", params.get("app_id"));
            transaction.put("amount", new BigDecimal(params.get("total_amount")+"").multiply(new BigDecimal("100")));
            transaction.put("tradeTime", params.get("gmt_payment"));
            //查询记录表（没有记录加一条，有记录并且返回的是fail，更新记录）
            Map<String,Object> m=orderInfoServiceImpl.findOrderTransRecord(outTradeNo,1);
            if(CommonUtil.isNotEmpty(m)){
                if(!m.get("transResult").equals("TRADE_SUCCESS")){
                    transaction.put("id", m.get("id"));
                    orderBusiServiceImpl.updateTransRecord(transaction);
                }
            }else{
                transaction.put("createTime", new Date());
                orderBusiServiceImpl.addOrderTransRecord(transaction);
            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            LoggerUtil.error(PayController.class,"verify_result:"+verify_result);
            if (verify_result) {     //验证成功

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
               if (tradeStatus.equals("TRADE_SUCCESS")) {
                    Map<String,Object> orderParment=new HashMap<>();
                    orderParment.put("totalOrderNo",outTradeNo);
                    List<Map<String,Object>> list=orderInfoServiceImpl.findOrder(orderParment);
                   LoggerUtil.error(PayController.class,"list:"+list);
                    if(CommonUtil.isEmpty(list)){
                        throw new BusinessException("订单号异常");
                    }
                    //如果订单状态为1，说明还没有付款，可以去更新订单状态
                    if(Integer.valueOf(list.get(0).get("orderStatus")+"")==1){
                        orderBusiServiceImpl.payOrder(list,1);
                    }
                }
                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                response.getWriter().write("success");    //请不要修改或删除

            } else {//验证失败
                LoggerUtil.error(PayController.class,"else:");
                response.getWriter().write("fail");
            }
        } catch (Exception e) {
            LoggerUtil.error(PayController.class,e);
            throw new BusinessException("支付宝支付失败");
        }
        return null;
    }


    //将xml转成map
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setExpandEntityReferences(false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
        org.w3c.dom.Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx=0; idx<nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        try {
            stream.close();
        }
        catch (Exception ex) {

        }
        return data;
    }
}
