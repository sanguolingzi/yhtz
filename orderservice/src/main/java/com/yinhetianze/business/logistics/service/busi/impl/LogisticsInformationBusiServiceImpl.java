package com.yinhetianze.business.logistics.service.busi.impl;

import com.yinhetianze.business.logistics.service.busi.LogisticsInformationBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.order.LogisticsInformationPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.logistics.mapper.busi.LogisticsInformationBusiMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogisticsInformationBusiServiceImpl implements LogisticsInformationBusiService{
    @Autowired
    private LogisticsInformationBusiMapper mapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

//    //appID
//    private String EBusinessID="1362415";
//    //加密私钥，快递鸟提供，注意保管，不要泄漏
//    private String AppKey="cac23d19-7bd1-49ef-afb7-c3f8b91e7ad1";
//    //测试请求url
//    //private String ReqURL = "http://testapi.kdniao.cc:8081/api/dist";
//
//    //正式请求url
//    private String ReqURL = "http://api.kdniao.cc/api/dist";

    @Override
    public int logisticsInformation(LogisticsInformationPojo logistics){
        String ReqURL = sysPropertiesUtils.getStringValue("kdn_ReqURL");
        String AppKey = sysPropertiesUtils.getStringValue("kdn_AppKey");
        String EBusinessID = sysPropertiesUtils.getStringValue("kdn_EBusinessID");
        //0.订阅记录失败 1.成功
        int logisticsre=0;
        try {
            String requestData="{'OrderCode': '"+logistics.getOrdersId()+"'," +
                    "'ShipperCode':'"+logistics.getExpressCode()+"'," +
                    "'LogisticCode':'"+logistics.getLogisticeCode()+"'," +
                    "'Sender':" +
                    "{" +
                    "'Name':'"+logistics.getSenderName()+"','Mobile':'"+logistics.getSenderMobile()+"','ProvinceName':'"+logistics.getSenderProvince()+"','CityName':'"+logistics.getSenderCity()+"','ExpAreaName':'"+logistics.getSenderArea()+"','Address':'"+logistics.getSenderAddress()+"'}," +
                    "'Receiver':" +
                    "{" +
                    "'Name':'"+logistics.getReceiverName()+"','Mobile':'"+logistics.getReceiverMobile()+"','ProvinceName':'"+logistics.getReceiverProvince()+"','CityName':'"+logistics.getReceiverCity()+"','ExpAreaName':'"+logistics.getReceiverArea()+"','Address':'"+logistics.getReceiverAddress()+"'}" + "}";
            Map<String, String> params = new HashMap<String, String>();
            //订阅内容
            params.put("RequestData", urlEncoder(requestData, "UTF-8"));
            params.put("EBusinessID", EBusinessID);
            //1008 订阅轨迹标识
            params.put("RequestType", "1008");
            String dataSign=encrypt(requestData, AppKey, "UTF-8");
            //签名
            params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
            //请求格式 2-json
            params.put("DataType", "2");
            String result=sendPost(ReqURL, params);
            //根据公司业务处理返回的信息......
            Map<String, Object> resultMap=CommonUtil.readFromString(result,HashMap.class);
            //订阅时间转换
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //预计到货时间转换
            SimpleDateFormat deliveryTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            logistics.setIsSubscription(resultMap.get("Success").toString());
            logistics.setSubscriptionTime(sdf.parse(resultMap.get("UpdateTime").toString()));
            if("true".equals(resultMap.get("Success").toString())){
                if(CommonUtil.isNotEmpty(resultMap.get("EstimatedDeliveryTime"))){
                    logistics.setEstimatedDeliveryTime(deliveryTime.parse(resultMap.get("EstimatedDeliveryTime").toString()));
                }
            }else{
                if(CommonUtil.isNotEmpty(resultMap.get("Reason"))){
                    logistics.setSubscriptionReason(resultMap.get("Reason").toString());
                }
            }
            logisticsre=mapper.insertSelective(logistics);
            if(logisticsre!=1){
                throw new BusinessException("订阅失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.error(LogisticsInformationBusiServiceImpl.class,e.toString());
        }
        return logisticsre;
    }

    @Override
    public int updateLogisticsInformation(LogisticsInformationPojo pojo){
        return mapper.updateByPrimaryKeySelective(pojo);
    }

    /**
     * MD5加密
     * @param str 内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     * @param str 内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException{
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     * @param content 内容
     * @param keyValue Appkey
     * @param charset 编码方式
     * @throws UnsupportedEncodingException ,Exception
     * @return DataSign签名
     */
    @SuppressWarnings("unused")
    private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
    {
        if (keyValue != null)
        {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if(param.length()>0){
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    System.out.println(entry.getKey()+":"+entry.getValue());
                }
                System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private static char[] base64EncodeChars = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/' };

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }
}