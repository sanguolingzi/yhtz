package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerWechatBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 微信授权后注册
 */

@Component
public class WechatRegeisterExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerWechatBusiService customerWechatBusiServiceImpl;

    @Autowired
    private RedisManager redisManager;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = customerWechatBusiServiceImpl.wxRegeister((BusiRegeisterModel) model);
        if (result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel) model;
        ErrorMessage errorMessage = new ErrorMessage();


        //TODO 微信注册

        if (CommonUtil.isEmpty(busiRegeisterModel.getCustomerCode())) {
            errorMessage.rejectNull("customerCode", null, "customerCode");
            return errorMessage;
        }

        if (CommonUtil.isEmpty(busiRegeisterModel.getPhone())) {
            errorMessage.rejectNull("phone", null, "phone");
            return errorMessage;
        }

        /*
        String smsCode = busiRegeisterModel.getSmsCode();
        String phone = busiRegeisterModel.getPhone();
        //--------------------- 校验短信验证码内容非空 以及 内容是否匹配 start-----------------------------------
        if(CommonUtil.isEmpty(smsCode)){
            errorMessage.rejectNull("smsCode",null,"短信验证码");
            return errorMessage;
        }

        Object code = redisManager.getValue(phone+ CustomerConstant.regeisterSufixKey);
        if(CommonUtil.isEmpty(code)){
            errorMessage.rejectError("smsCode","BC0029","");
            return errorMessage;
        }

        if(!code.toString().equals(smsCode)){
            errorMessage.rejectError("smsCode","BC0053","");
            return errorMessage;
        }
        //--------------------- 校验短信验证码内容非空 以及 内容是否匹配 start-----------------------------------
        */

        return null;
    }

}
