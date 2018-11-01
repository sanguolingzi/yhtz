package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 游戏Id绑定
 */

@Component
public class GetGameIdBindExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        //获取登陆token里面的customerId
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(tokenUser == null){
            throw new BusinessException("BC0001", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectById(tokenUser.getId());
        if(CommonUtil.isNotEmpty(busiCustomerPojo.getGameId())){
            throw new BusinessException("您已绑定游戏账号");
        }
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        //封装请求参数进行签名
        //获取当当前时间戳
        //String timestamp= String.valueOf(System.currentTimeMillis());
        //获取当前需请求接口的渠道编码
        String  channelCode="CC1002";
        //获取当前请求接口端的渠道秘钥
        CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
        HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
        Map map = channelInfo.get(channelCode);
        String channelSecret = map.get("channelSecret").toString();
        Map<String, Object> signMap = new HashMap<String, Object>();
        signMap.put("customerId", busiCustomerPojo.getId());
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
        String sign = MD5CoderUtil.encode(stringSignTemp).toUpperCase();
       /* try{
            //封装请求第三方参数
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("customerId", gameBusinessModel.getCustomerId());
            paramsMap.put("sign", sign);
            String  gameString= HttpClientUtil.doPost("https://yqwx.gaoqi99.cn/api/bind/wechat", paramsMap, null);
            // 解析返回信息字符串，转成map
            Map<String, Object> resultMap = CommonUtil.readFromString(gameString, HashMap.class);
            if(CommonUtil.isNotEmpty(resultMap) && "0".equals(resultMap.get("code").toString())){
                return resultMap.get("data");
            }
        }  catch (Exception e) {
        e.printStackTrace();
        LoggerUtil.error(GetUserBindExecutor.class,e.toString());
    }*/
        Map maps = new HashMap();
        maps.put("customerId",busiCustomerPojo.getId());
        maps.put("sign",sign);
        return maps;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

//        if(CommonUtil.isEmpty(gameBusinessModel.getCustomerId())){
//            errorMessage.rejectNull("customerId",null,"用户ID");
//            return errorMessage;
//        }
        return errorMessage;
    }
}
