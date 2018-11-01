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
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 商城提供给游戏用户注册接口
 */

@Component
public class GetCustomerRegExecutor extends AbstractRestBusiExecutor<Object>{

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setGameId(gameBusinessModel.getGameId());
        //查询该gameId在平台是否绑定
        BusiCustomerPojo  customerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isNotEmpty(customerPojo)){
            throw new BusinessException("gameId已绑定商城用户，无法再次注册");
        }
        BusiRegeisterModel busiRegeisterModel = new BusiRegeisterModel();
        busiRegeisterModel.setGameId(gameBusinessModel.getGameId());
        Integer customerId = customerBusiServiceImpl.addRegeisterCustomer(busiRegeisterModel);
        if(CommonUtil.isEmpty(customerId)){
            return "failure";
        }
        //增加用户信息 昵称 头像 性别 等
        BusiCustomerPojo paraPojo = new BusiCustomerPojo();
        paraPojo.setNickName(gameBusinessModel.getNickName());
        paraPojo.setSex(gameBusinessModel.getSex().shortValue());
        paraPojo.setPhotoUrl(gameBusinessModel.getHeadUrl());
        paraPojo.setId(customerId);
        customerBusiServiceImpl.updateByPrimaryKeySelective(paraPojo);
        return customerId;
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
            String checkSignString = "channelCode=" + channelCode + "&channelSecret=" + channelSecret + "&gameId=" + gameBusinessModel.getGameId();
            if(CommonUtil.isNotEmpty(gameBusinessModel.getNickName())){
                 checkSignString = checkSignString+"&nickName="+gameBusinessModel.getNickName();
            }
            if(CommonUtil.isNotEmpty(gameBusinessModel.getSex())){
                checkSignString = checkSignString+"&sex="+gameBusinessModel.getSex();
            }
            if(CommonUtil.isNotEmpty(gameBusinessModel.getHeadUrl())){
                checkSignString = checkSignString+"&headUrl="+gameBusinessModel.getHeadUrl();
            }
            String checkSign = MD5CoderUtil.encode(checkSignString);
            if (!checkSign.equals(gameBusinessModel.getSign())) {
                errorMessage.rejectError("checkSign", "BC0057", "签名错误");
                return errorMessage;
            }
        }

        return errorMessage;
    }
}
