package com.yinhetianze.business.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;


@Component
public class Alipay {

    public void alipay(String tradeMoney,String orderNo,String aliNotifyUrl,String returnUrl,HttpServletResponse response) throws AlipayApiException {

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderNo;
        // 订单名称，必填
        String subject = "玩客优品";
        // 付款金额，必填
        String total_amount = tradeMoney;
        // 商品描述，可空
        String body = "";
        // 超时时间 可空
        String timeout_express = "2m";
        // 销售产品码 必填
        String product_code = "QUICK_WAP_WAY";
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(aliNotifyUrl);
        // 设置同步地址
        alipay_request.setReturnUrl(returnUrl);

        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            ResponseData<String> responseData  = new ResponseData();
            Result result=new Result();
            responseData.setResultInfo(result);
            responseData.setData(form);
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(CommonUtil.objectToJsonString(responseData));//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            throw new AlipayApiException(e);
        }
    }

    public void aliPagePay(String tradeMoney,String orderNo,String aliNotifyUrl,String returnUrl,HttpServletResponse response) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(aliNotifyUrl);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no =orderNo;
        //付款金额，必填
        String total_amount =tradeMoney;
        //订单名称，必填
        String subject = "友旗有品";
        //商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            ResponseData<String> responseData  = new ResponseData();
            Result resultData=new Result();
            responseData.setResultInfo(resultData);
            responseData.setData(result);
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(CommonUtil.objectToJsonString(responseData));//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            throw new AlipayApiException(e);
        }
    }

    public String appPay(String tradeMoney,String orderNo,String aliNotifyUrl) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", AlipayConfig.CHARSET,AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("友旗有品支付");
        model.setSubject("App支付");
        model.setOutTradeNo(orderNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(tradeMoney);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(aliNotifyUrl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse resp = alipayClient.sdkExecute(request);
            return resp.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            throw new AlipayApiException(e);
        }

    }


    /**
     * 支付宝退款接口
     * @param refundAmount
     * @param outTradeNo
     * @param outRequestNo
     * @param response
     * @throws AlipayApiException
     */
    public AlipayTradeRefundResponse refund (String refundAmount,String outTradeNo,String outRequestNo,HttpServletResponse response) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        AlipayTradeRefundModel model=new AlipayTradeRefundModel();
        model.setOutTradeNo(outTradeNo);
        model.setRefundAmount(refundAmount);
        model.setOutRequestNo(outRequestNo);
        alipayRequest.setBizModel(model);

        AlipayTradeRefundResponse alipayResponse =client.execute(alipayRequest);
        return alipayResponse;
    }

}
