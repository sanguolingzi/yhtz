package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerRecommendRelationBusiService;
import com.yinhetianze.business.customer.service.busi.GameRegisterRecordBusiService;
import com.yinhetianze.business.customer.service.busi.impl.CustomerRecommendRelationBusiServiceImpl;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import com.yinhetianze.pojo.customer.GameRegisterRecordPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
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
public class GetRecommendedBindExecutor extends AbstractRestBusiExecutor<Object>  {
    @Autowired
    private CustomerRecommendRelationBusiService customerRecommendRelationBusiServiceImpl;

    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private GameRegisterRecordBusiService gameRegisterRecordBusiServiceImpl;

    @Autowired
    private RedisManager redisManager;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        BusiCustomerRecommendRelationPojo relationPojo = new BusiCustomerRecommendRelationPojo();
        int dataCheck=1;
        Object obj=null;
        //查询redis里面是否有绑定关系记录
        try{
            obj= redisManager.getValue(gameBusinessModel.getGameId().toString()+"_relation");
        }catch (Exception e){

        }
        if(CommonUtil.isEmpty(obj)){
            BusiCustomerRecommendRelationPojo recommendRelationPojo= customerRecommendRelationInfoServiceImpl.selectRecommendUser(gameBusinessModel.getGameId());
            if(CommonUtil.isEmpty(recommendRelationPojo)){
                try{
                    relationPojo.setRecomedGameId(gameBusinessModel.getGameId());
                    relationPojo.setRecomGameId(gameBusinessModel.getpGameId());
                    relationPojo.setShowId(gameBusinessModel.getShowId());
                    relationPojo.setpShowId(gameBusinessModel.getpShowId());
                    customerRecommendRelationBusiServiceImpl.bindRelation(relationPojo);
                    try{
                        redisManager.setValue(gameBusinessModel.getGameId().toString()+"_relation",gameBusinessModel.getGameId());
                    }catch (Exception e){

                    }
                }catch (Exception e){
                    dataCheck=0;
                    GameRegisterRecordPojo gameRegisterRecordPojo = new GameRegisterRecordPojo();
                    gameRegisterRecordPojo.setGameId(gameBusinessModel.getGameId());
                    gameRegisterRecordPojo.setpGameId(gameBusinessModel.getpGameId());
                    gameRegisterRecordPojo.setStatus((byte) 0);
                    gameRegisterRecordBusiServiceImpl.insertgameRegister(gameRegisterRecordPojo);
                    LoggerUtil.error(GetRecommendedBindExecutor.class,"bindRelation error:"+e.getMessage());
                }
            }else{
                try{
                    redisManager.setValue(gameBusinessModel.getGameId().toString()+"_relation",gameBusinessModel.getGameId());
                }catch (Exception e){

                }
            }
        }
        return dataCheck;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(gameBusinessModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"gameId");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getpGameId())){
            errorMessage.rejectNull("pGameId",null,"推荐人gameId");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getChannelCode())){
            errorMessage.rejectNull("channelCode",null,"渠道编码");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getShowId())){
            errorMessage.rejectNull("showId",null,"showId");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getpShowId())){
            errorMessage.rejectNull("pShowId",null,"pShowId");
            return errorMessage;
        }
        if(CommonUtil.isNull(gameBusinessModel.getSign())){
            errorMessage.rejectNull("sign",null,"签名参数");
            return errorMessage;
        }else{
            String  channelCode=gameBusinessModel.getChannelCode();
            //获取当前请求接口端的渠道秘钥
            CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
            HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
            Map map = channelInfo.get(channelCode);
            String channelSecret = map.get("channelSecret").toString();
            String checkSignString="channelCode="+channelCode+"&channelSecret="+channelSecret+"&gameId="+gameBusinessModel.getGameId()+"&pGameId="+gameBusinessModel.getpGameId()+"&showId="+gameBusinessModel.getShowId()+"&pShowId="+gameBusinessModel.getpShowId();
            String checkSign=MD5CoderUtil.encode(checkSignString);
            if(!checkSign.equals(gameBusinessModel.getSign())){
                errorMessage.rejectError("checkSign","BC0057","签名错误");
                return errorMessage;
            }
    }

        return errorMessage;
    }
}
