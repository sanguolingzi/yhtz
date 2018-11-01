package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.core.utils.SecurityCode;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 商城提供给游戏用户注册接口
 */

@Component
public class GetCustomerLoginExecutor extends AbstractRestBusiExecutor<Object>{

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setGameId(gameBusinessModel.getGameId());
        //生成对应的key返回给游戏端
        Map keyMap =new HashMap();
        //查询该gameId在平台是否绑定
        BusiCustomerPojo  customerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isNotEmpty(customerPojo)){
            //已经绑定则进行登录操作 生成key
            BusiCustomerModel busiCustomerModel=new BusiCustomerModel();
            busiCustomerModel.setId(customerPojo.getId());
            busiCustomerModel.setCheckPassword(false);
            Map<String,Object> userInfo= customerInfoServiceImpl.login(busiCustomerModel);
            if(CommonUtil.isNotEmpty(userInfo)){
                keyMap.put("token",userInfo);
            }

        }else{
            keyMap.put("gameId",gameBusinessModel.getGameId());
            keyMap.put("pGameId",gameBusinessModel.getpGameId());

        }
        String key = MD5CoderUtil.encode(gameBusinessModel.getGameId()+String.valueOf(System.nanoTime())+ SecurityCode.getSecurityCode());
        redisUserDetails.saveGameKey(key,keyMap);
        Map dataCheck =new HashMap();
        dataCheck.put("gameToken",key);
        dataCheck.put("dataCheck",0);
        return dataCheck;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        ErrorMessage errorMessage = new ErrorMessage();

        if (CommonUtil.isEmpty(gameBusinessModel.getGameId())) {
            errorMessage.rejectNull("gameId", null, "游戏用户ID");
            return errorMessage;
        }
        if (CommonUtil.isEmpty(gameBusinessModel.getChannelCode())) {
            errorMessage.rejectNull("channelCode", null, "渠道编码");
            return errorMessage;
        }
        if (CommonUtil.isNull(gameBusinessModel.getSign())) {
            errorMessage.rejectNull("sign", null, "签名参数");
            return errorMessage;
        } else {
            String channelCode = gameBusinessModel.getChannelCode();
            //获取当前请求接口端的渠道秘钥
            CacheData<HashMap<String,Map<String,Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
            HashMap<String,Map<String,Object>> channelInfo = sysChannelCacheData.getCacheData();
            Map map = channelInfo.get(channelCode);
            String channelSecret = map.get("channelSecret").toString();
            String checkSignString = "channelCode=" + channelCode + "&channelSecret=" + channelSecret + "&gameId=" + gameBusinessModel.getGameId() + "&pGameId="+gameBusinessModel.getpGameId();
            String checkSign = MD5CoderUtil.encode(checkSignString);
            if (!checkSign.equals(gameBusinessModel.getSign())) {
                errorMessage.rejectError("checkSign", "BC0057", "签名错误");
                return errorMessage;
            }
        }

        return errorMessage;
    }
}
