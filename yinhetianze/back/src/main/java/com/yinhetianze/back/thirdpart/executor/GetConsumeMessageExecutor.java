package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetConsumeMessageExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        //封装请求参数进行签名
        //获取当当前时间戳
        String timestamp= String.valueOf(System.currentTimeMillis());
        //获取当前需请求接口的渠道编码
        String  channelCode="CC1002";
        //获取当前请求接口端的渠道秘钥
        CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
        HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
        Map map = channelInfo.get(channelCode);
        //订单号生成
        String ordersNo =CommonUtil.getSerialnumber();
        String channelSecret = map.get("channelSecret").toString();
        Map<String, Object> signMap = new HashMap<String, Object>();
        signMap.put("tradeNo", ordersNo);
        signMap.put("tradeType",gameBusinessModel.getTradeType());
        signMap.put("tradeDesc", gameBusinessModel.getTradeDesc());
        signMap.put("amount", gameBusinessModel.getAmount());
        signMap.put("gameId", gameBusinessModel.getGameId());
        //signMap.put("customerId", gameBusinessModel.getCustomerId());
        signMap.put("paymentTime",gameBusinessModel.getPaymentTime());
        signMap.put("timestamp", timestamp);
        signMap.put("channelCode", channelCode);
        signMap.put("channelSecret", channelSecret);
        signMap.put("num", gameBusinessModel.getNum());
        List<String> keys=new ArrayList<String>(signMap.keySet());
        Collections.sort(keys);
        String stringSignTemp="";
        for(int i=0;i<keys.size();i++) {
            if (i != keys.size() - 1) {
                stringSignTemp = stringSignTemp + keys.get(i) + "=" + signMap.get(keys.get(i)) + "&";
            } else {
                stringSignTemp = stringSignTemp + keys.get(i) + "=" + signMap.get(keys.get(i));
            }
        }
             try {
                String sign = MD5CoderUtil.encode(stringSignTemp).toUpperCase();
                //封装请求第三方参数
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put("tradeNo", ordersNo);
                paramsMap.put("tradeType",gameBusinessModel.getTradeType());
                paramsMap.put("tradeDesc", gameBusinessModel.getTradeDesc());
                paramsMap.put("amount", gameBusinessModel.getAmount());
                paramsMap.put("gameId", gameBusinessModel.getGameId());
                //paramsMap.put("customerId", gameBusinessModel.getCustomerId());
                paramsMap.put("paymentTime",gameBusinessModel.getPaymentTime());
                paramsMap.put("timestamp", timestamp);
                paramsMap.put("num", gameBusinessModel.getNum());
                paramsMap.put("sign", sign);
                String  gameString= HttpClientUtil.doPost("http://yqwx.gaoqi99.cn/api/payNotify/YHTZ", paramsMap, null);
                // 解析返回信息字符串，转成map
                Map<String, Object> resultMap = CommonUtil.readFromString(gameString, HashMap.class);
                if(CommonUtil.isNotEmpty(resultMap) && "0".equals(resultMap.get("code").toString())){
                    return resultMap.get("data");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LoggerUtil.error(GetUserBindExecutor.class,e.toString());
            }
        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        /*if(CommonUtil.isEmpty(gameBusinessModel.getTradeNo())){
            errorMessage.rejectNull("tradeNo",null,"订单号");
            return errorMessage;
        }*/
        if(CommonUtil.isEmpty(gameBusinessModel.getTradeType())){
            errorMessage.rejectNull("tradeType",null,"商品类型");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getTradeDesc())){
            errorMessage.rejectNull("tradeDesc",null,"订单描述");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getAmount())){
            errorMessage.rejectNull("amount",null,"订单金额");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"游戏用户Id");
            return errorMessage;
        }
        /*if(CommonUtil.isEmpty(gameBusinessModel.getCustomerId())){
            errorMessage.rejectNull("customerId",null,"用户Id");
            return errorMessage;
        }*/
        if(CommonUtil.isEmpty(gameBusinessModel.getPaymentTime())){
            errorMessage.rejectNull("paymentTime",null,"支付时间");
            return errorMessage;
        }
        return errorMessage;
    }
}
