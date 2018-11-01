package com.yinhetianze.business.customer.executor;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.customer.model.BusiWechatModel;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取微信openId
 */

@Component
public class GetWechatOpenIdExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiWechatModel busiWechatModel = (BusiWechatModel)model;

        BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
        busiCustomerWechatPojo.setCustomerId(busiWechatModel.getCustomerId());
        busiCustomerWechatPojo.setIdType(busiWechatModel.getIdType());
        busiCustomerWechatPojo = customerWechatInfoServiceImpl.select(busiCustomerWechatPojo);
        JSONObject json = new JSONObject();
        if(busiCustomerWechatPojo == null){
            json.put("openId","");
            return json.toJSONString();
        }

        json.put("openId",busiCustomerWechatPojo.getOpenId());
        return json.toJSONString();
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {

        BusiWechatModel busiWechatModel = (BusiWechatModel)model;

        if(CommonUtil.isEmpty(busiWechatModel.getCustomerId())){
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.rejectNull("customerId",null,"customerId");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiWechatModel.getCustomerId())){
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.rejectNull("customerId",null,"customerId");
            return errorMessage;
        }

        return null;
    }
}
