package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerRecommendRelationBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
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
public class GetCustomerReferrerExecutor extends AbstractRestBusiExecutor<Object>{

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private CustomerRecommendRelationBusiService customerRecommendRelationBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setGameId(gameBusinessModel.getGameId());
        //查询该gameId在平台是否绑定
        BusiCustomerPojo  customerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isEmpty(customerPojo)){
            throw new BusinessException("gameId未绑定商城用户");
        }
        //查询gameId是否在平台已绑定推荐人 已绑定推荐人无法再修改推荐关系
        BusiCustomerRecommendRelationPojo relationPojo = customerRecommendRelationInfoServiceImpl.selectRecommendUser(customerPojo.getId());
        if(CommonUtil.isNotEmpty(relationPojo)){
            throw new BusinessException("gameId已存在推荐人");
        }
        //查询referrerId 是否绑定商城用户
        busiCustomerPojo.setGameId(gameBusinessModel.getReferrerId());
        BusiCustomerPojo  referrerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isEmpty(referrerPojo)){
            throw new BusinessException("referrerId未绑定商城用户");
        }
        BusiCustomerRecommendRelationPojo recordPojo=new BusiCustomerRecommendRelationPojo();
        recordPojo.setRecomedGameId(customerPojo.getId());
        recordPojo.setRecomGameId(referrerPojo.getId());
        //建立绑定关系
        int relation=customerRecommendRelationBusiServiceImpl.bindRelation(recordPojo);
        if(relation!=1){
            throw new BusinessException("更新失败");
        }

        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        ErrorMessage errorMessage = new ErrorMessage();

        if (CommonUtil.isEmpty(gameBusinessModel.getGameId())) {
            errorMessage.rejectNull("gameId", null, "游戏用户ID");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getReferrerId())){
            errorMessage.rejectNull("referrerId", null, "推荐人ID");
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
            String checkSignString = "channelCode=" + channelCode + "&channelSecret=" + channelSecret + "&gameId=" + gameBusinessModel.getGameId()+"&referrerId=" + gameBusinessModel.getReferrerId();
            String checkSign = MD5CoderUtil.encode(checkSignString);
            if (!checkSign.equals(gameBusinessModel.getSign())) {
                errorMessage.rejectError("checkSign", "BC0057", "签名错误");
                return errorMessage;
            }
        }

        return errorMessage;
    }
}
