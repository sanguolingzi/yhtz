package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetGameCustomerExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private GameRecordInfoService gameRecordInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        //封装调用接口需请求的参数
        //获取当前游戏Id
        String gameId=gameBusinessModel.getGameId().toString();
        //获取当当前时间戳
        String timestamp= String.valueOf(System.currentTimeMillis());
        //获取当前需请求接口的渠道编码
        String  channelCode="CC1002";
        //获取当前请求接口端的渠道秘钥
        CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
        HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
        Map map = channelInfo.get(channelCode);
        String channelSecret = map.get("channelSecret").toString();

        //签名算法
       // String stringSignTemp="channelCode="+channelCode+"&gameId="+gameId+"&timestamp="+timestamp+"&channelSecret="+channelSecret;
        //获取sign
       // String sign=MD5CoderUtil.encode(stringSignTemp).toUpperCase();
        //封装签名算法参数
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("gameId", gameId);
        userMap.put("timestamp", timestamp);
        userMap.put("channelCode", channelCode);
        userMap.put("channelSecret", channelSecret);
        List<String> keys=new ArrayList<String>(userMap.keySet());
        Collections.sort(keys);
        String stringSignTemp="";
        for(int i=0;i<keys.size();i++){
            if(i!=keys.size()-1){
                stringSignTemp=stringSignTemp+keys.get(i)+"="+userMap.get(keys.get(i))+"&";
            }else{

                stringSignTemp=stringSignTemp+keys.get(i)+"="+userMap.get(keys.get(i));
            }
            //stringSignTemp=keys.get(0)+"="+userMap.get(keys.get(0))+"&"+keys.get(1)+"="+userMap.get(keys.get(1))+"&"+keys.get(2)+"="+userMap.get(keys.get(2));
        }
        String sign=MD5CoderUtil.encode(stringSignTemp).toUpperCase();
        try {
            //封装请求第三方参数
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("sign", sign);
            paramsMap.put("gameId", gameId);
            paramsMap.put("timestamp", timestamp);
            String  gameString=HttpClientUtil.doPost("http://yqwx.gaoqi99.cn/api/user/info", paramsMap, null);
            // 解析返回信息字符串，转成map
            Map<String, Object> resultMap = CommonUtil.readFromString(gameString, HashMap.class);
            if(CommonUtil.isNotEmpty(resultMap) && "0".equals(resultMap.get("code").toString())){
               return resultMap.get("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.error(GetGameCustomerExecutor.class,e.toString());
        }
        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(gameBusinessModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"游戏ID");
            return errorMessage;
        }
        return errorMessage;
    }
}
