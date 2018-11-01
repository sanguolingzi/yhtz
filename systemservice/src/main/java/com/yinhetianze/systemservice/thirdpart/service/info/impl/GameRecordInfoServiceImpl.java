package com.yinhetianze.systemservice.thirdpart.service.info.impl;

import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.order.GameConsumeMessagePojo;
import com.yinhetianze.pojo.thirdpart.GameRecordPojo;
import com.yinhetianze.systemservice.thirdpart.mapper.busi.GameConsumeMessageBusiMapper;
import com.yinhetianze.systemservice.thirdpart.mapper.info.GameConsumeMessageInfoMapper;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.thirdpart.mapper.info.GameRecordInfoMapper;

import java.util.*;

@Service
public class GameRecordInfoServiceImpl implements GameRecordInfoService
{
    @Autowired
    private GameRecordInfoMapper mapper;

    @Autowired
    private GameConsumeMessageBusiMapper gameConsumeMessageBusiMapper;

    @Autowired
    private GameConsumeMessageInfoMapper gameConsumeMessageInfoMapper;

    @Override
    public List<GameConsumeMessagePojo> selectGameRecord(GameRecordModel gameRecordModel){
       /* GameConsumeMessagePojo gameConsumeMessagePojo =new GameConsumeMessagePojo();
        if(CommonUtil.isNotEmpty(gameRecordModel.getGameId())){
            gameConsumeMessagePojo.setGameId(Integer.parseInt(gameRecordModel.getGameId()));
        }*/
        return gameConsumeMessageInfoMapper.selectGameRecord(gameRecordModel);
    }

    @Override
    public String consumeMessage(GameBusinessModel gameBusinessModel){
        if(CommonUtil.isEmpty(gameBusinessModel.getTradeNo())){
            return "订单号不能为空";
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getTradeType())){
            return "商品类型不能为空";
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getTradeDesc())){
            return "订单描述不能为空";
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getAmount())){
            return "订单金额不能为空";
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getGameId())){
            return "游戏用户Id不能为空";
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getCustomerId())){
            return "用户Id不能为空";
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getPaymentTime())){
            return "支付时间不能为空";
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getNum())){
            return "房卡数量不能为空";
        }
        //方法返回成功状态success 失败状态failure成功 默认成功
        String type="success";
        //获取当当前时间戳
        String timestamp= String.valueOf(System.currentTimeMillis());
        //获取当前需请求接口的渠道编码
        String  channelCode="CC1002";
        //获取当前请求接口端的渠道秘钥
        CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
        HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
        Map map = channelInfo.get(channelCode);
        String channelSecret = map.get("channelSecret").toString();
        Map<String, Object> signMap = new HashMap<String, Object>();
        signMap.put("tradeNo", gameBusinessModel.getTradeNo());
        signMap.put("tradeType",gameBusinessModel.getTradeType());
        signMap.put("tradeDesc", gameBusinessModel.getTradeDesc());
        signMap.put("amount", gameBusinessModel.getAmount().intValue());
        signMap.put("num", gameBusinessModel.getNum());
        signMap.put("gameId", gameBusinessModel.getGameId());
        signMap.put("paymentTime",gameBusinessModel.getPaymentTime());
        signMap.put("timestamp", timestamp);
        signMap.put("channelCode", channelCode);
        signMap.put("channelSecret", channelSecret);
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
        Map<String, Object> resultMap=new HashMap<>();
        try {
            String sign = MD5CoderUtil.encode(stringSignTemp).toUpperCase();
            //封装请求第三方参数
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("tradeNo", gameBusinessModel.getTradeNo());
            paramsMap.put("tradeType",gameBusinessModel.getTradeType());
            paramsMap.put("tradeDesc", gameBusinessModel.getTradeDesc());
            paramsMap.put("amount", gameBusinessModel.getAmount().intValue());
            paramsMap.put("gameId", gameBusinessModel.getGameId());
            paramsMap.put("paymentTime",gameBusinessModel.getPaymentTime());
            paramsMap.put("num", gameBusinessModel.getNum());
            paramsMap.put("timestamp", timestamp);
            paramsMap.put("sign", sign);
            String  gameString= HttpClientUtil.doPost("http://yqwx.gaoqi99.cn/api/payNotify/YHTZ", paramsMap, null);
            // 解析返回信息字符串，转成map
            resultMap = CommonUtil.readFromString(gameString, HashMap.class);
        } catch (Exception e) {
            type="failure";
            e.printStackTrace();
            LoggerUtil.error(GameRecordInfoServiceImpl.class,e.toString());
        }finally {
            if(CommonUtil.isEmpty(resultMap)){
                resultMap.put("code",1);
                resultMap.put("msg","返回数据为空");
            }
            GameConsumeMessagePojo gameConsumeMessagePojo=new GameConsumeMessagePojo();
            gameConsumeMessagePojo.setTradeNo(gameBusinessModel.getTradeNo());
            gameConsumeMessagePojo.setTradeType(Integer.valueOf(gameBusinessModel.getTradeType()+""));
            gameConsumeMessagePojo.setTradeDesc(gameBusinessModel.getTradeDesc());
            gameConsumeMessagePojo.setAmount(gameBusinessModel.getAmount());
            gameConsumeMessagePojo.setNum(gameBusinessModel.getNum());
            gameConsumeMessagePojo.setGameId(gameBusinessModel.getGameId());
            gameConsumeMessagePojo.setCustomerId(Integer.valueOf(gameBusinessModel.getCustomerId()));
            gameConsumeMessagePojo.setPaymentTime(new Date());
            gameConsumeMessagePojo.setCode(Short.valueOf(resultMap.get("code")+""));
            if("0".equals(resultMap.get("code").toString())){
                gameConsumeMessagePojo.setResult(resultMap.get("data").toString());
            }else{
                gameConsumeMessagePojo.setResult(resultMap.get("msg")+"");
            }
            gameConsumeMessageBusiMapper.insertSelective(gameConsumeMessagePojo);
        }
        return type;
    }
}