package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiWechatModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 微信登录
 */

@Component
public class WechatLoginExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
        BusiWechatModel busiWechatModel = (BusiWechatModel)model;
        busiCustomerWechatPojo.setCustomerCode(busiWechatModel.getCustomerCode());
        busiCustomerWechatPojo = customerWechatInfoServiceImpl.select(busiCustomerWechatPojo);
        if(busiCustomerWechatPojo == null
                || busiCustomerWechatPojo.getOpenId() == null){
            //throw new BusinessException("BC0009");
            LoggerUtil.error(WechatLoginExecutor.class,"customerCode:"+busiWechatModel.getCustomerCode());
            Map paraMap = new HashMap();
            paraMap.put("code","1");
            paraMap.put("msg","failed");
            return paraMap;
        }

        BusiCustomerModel busiCustomerModel = new BusiCustomerModel();
        busiCustomerModel.setId(busiCustomerWechatPojo.getCustomerId());
        busiCustomerModel.setCheckPassword(false);
        //redisManager.setValue(busiCustomerModel.getPhone()+ CustomerConstant.tokenSufixKey,System.nanoTime(), RedisManager.TWO_HOUR);
        Map paraMap = customerInfoServiceImpl.login(busiCustomerModel);
        paraMap.put("code","0");
        paraMap.put("msg","success");
        return paraMap;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        return null;
    }
}
