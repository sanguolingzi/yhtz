package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerCenterModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 校验用户数据的设置情况
 */

@Component
public class CheckCustomerParamExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> returnMap = new HashMap();
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(tokenUser == null){
            throw new BusinessException("BC0001", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectById(tokenUser.getId());
        String hasLoginPassword = CommonUtil.isEmpty(busiCustomerPojo.getLoginPassword())?"1":"0";
        returnMap.put("hasLoginPassword",hasLoginPassword);
        String hasPayPassword = CommonUtil.isEmpty(busiCustomerPojo.getPayPassword())?"1":"0";
        returnMap.put("hasPayPassword",hasPayPassword);
        String hasPhone = CommonUtil.isEmpty(busiCustomerPojo.getPhone())?"1":"0";
        returnMap.put("hasPhone",hasPhone);

        String hasGameId = CommonUtil.isEmpty(busiCustomerPojo.getGameId())?"1":"0";
        returnMap.put("hasGameId",hasGameId);

        BusiCustomerWechatPojo busiCustomerWechatPojoH5 =customerWechatInfoServiceImpl.selectByCustomerId(tokenUser.getId(),(short)1);
        String hasWeChat = CommonUtil.isEmpty(busiCustomerWechatPojoH5)?"1":"0";
        returnMap.put("hasWeChat",hasWeChat);

        BusiCustomerWechatPojo busiCustomerWechatPojoApp =customerWechatInfoServiceImpl.selectByCustomerId(tokenUser.getId(),(short)2);
        String hasWeChatApp = CommonUtil.isEmpty(busiCustomerWechatPojoApp)?"1":"0";
        returnMap.put("hasAppWeChat",hasWeChatApp);

        return returnMap;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiCustomerCenterModel busiRegeisterModel = (BusiCustomerCenterModel)model;

        if(CommonUtil.isEmpty(busiRegeisterModel.getToken())){
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        return null;
    }
}
