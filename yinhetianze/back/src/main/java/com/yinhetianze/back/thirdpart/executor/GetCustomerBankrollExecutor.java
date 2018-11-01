package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询平台用户信息接口（反向接口）
 */

@Component
public class GetCustomerBankrollExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = new BusiCustomerBankrollPojo();
        busiCustomerBankrollPojo.setGameId(gameBusinessModel.getGameId());
        busiCustomerBankrollPojo =customerBankrollInfoServiceImpl.selectOne(busiCustomerBankrollPojo);
        Map map=new HashMap();
        if(CommonUtil.isEmpty(busiCustomerBankrollPojo)){
            throw new BusinessException("用户不存在");
        }
        map.put("rewardAmount",busiCustomerBankrollPojo.getRewardAmount().divide(new BigDecimal(100)));
        map.put("starCoin",busiCustomerBankrollPojo.getStarCoin().divide(new BigDecimal(100)));
        map.put("dataCheck",0);
        return map;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(gameBusinessModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"gameId");
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
