package com.yinhetianze.back.thirdpart.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.busi.CustomerRecommendRelationBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.business.product.service.ProductFresherRewardInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询平台用户信息接口（反向接口）
 */

@Component
public class GetCustomerDetailExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private ProductFresherRewardInfoService productFresherRewardInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setGameId(gameBusinessModel.getGameId());
        //根据gameId查询出用户是否存在
        BusiCustomerPojo Pojo= customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isEmpty(Pojo)){
            throw new BusinessException("用户不存在");
        }
        //根据customerId查询微信信息
        BusiCustomerWechatPojo BusiCustomerWechatPojo = new BusiCustomerWechatPojo();
        BusiCustomerWechatPojo.setCustomerId(Pojo.getId());
        List<BusiCustomerWechatPojo> wechatPojo=customerWechatInfoServiceImpl.getOneCustomer(BusiCustomerWechatPojo);
        Map gameMap= new HashMap();
        gameMap.put("customerId",Pojo.getId());
        gameMap.put("isMember",Pojo.getIsMember());
        gameMap.put("isPartner",Pojo.getIsPartner());
        gameMap.put("gameId",Pojo.getGameId());
        //根据customerId查询推荐关系表
        BusiCustomerRecommendRelationPojo recommendRelationPojo =customerRecommendRelationInfoServiceImpl.selectRecommendUser(Pojo.getGameId());
        if(CommonUtil.isEmpty(recommendRelationPojo)){
            gameMap.put("recomCustomerId",null);
        }else{
            gameMap.put("recomCustomerId",recommendRelationPojo.getRecomGameId());
        }
        if(wechatPojo.size()>0){
            try {
                gameMap.put("nickName", URLDecoder.decode(wechatPojo.get(0).getNickName(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            gameMap.put("sex",wechatPojo.get(0).getSex());
        }else{
            gameMap.put("nickName",null);
            gameMap.put("sex",null);
        }
        gameMap.put("dataCheck",0);
        //根据gameId查询用户是否能兑换新手礼品 0.没有兑换过 1.兑换过
        ProductFresherRewardPojo productFresherRewardPojo = new ProductFresherRewardPojo();
        productFresherRewardPojo.setGameId(gameBusinessModel.getGameId());
        productFresherRewardPojo=productFresherRewardInfoServiceImpl.selectFresherReward(productFresherRewardPojo);
        if(CommonUtil.isNotEmpty(productFresherRewardPojo) && productFresherRewardPojo.getStatus()==1 && productFresherRewardPojo.getHandleStatus()==0){
            gameMap.put("status",1);
        }else{
            gameMap.put("status",0);
        }
        return gameMap;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(gameBusinessModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"游戏ID");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getChannelCode())){
            errorMessage.rejectNull("channelCode",null,"渠道编码");
            return errorMessage;
        }
        if(CommonUtil.isNull(gameBusinessModel.getSign())){
            errorMessage.rejectNull("sign",null,"签名参数");
            return errorMessage;
        }else{
            //获取连接地址后面参数 进行加密
//            String checkSignString=request.getQueryString();
//            String checkSign=MD5CoderUtil.encode(checkSignString);
            CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
            HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
            Map map = channelInfo.get(gameBusinessModel.getChannelCode());
            String channelSecret = map.get("channelSecret").toString();
            String checkSignString="channelCode="+gameBusinessModel.getChannelCode()+"&channelSecret="+channelSecret+"&gameId="+gameBusinessModel.getGameId();
            String checkSign=MD5CoderUtil.encode(checkSignString);
            if(!checkSign.equals(gameBusinessModel.getSign())){
                errorMessage.rejectError("checkSign","BC0057","签名错误");
                return errorMessage;
            }
        }

        return errorMessage;
    }
}
