package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiUpdatePhoneModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 修改手机
 */

@Component
public class UpdateCustomerPhoneExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private RedisManager redisManager;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiUpdatePhoneModel busiUpdatePhoneModel = (BusiUpdatePhoneModel)model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(busiUpdatePhoneModel.getId());
        //busiCustomerPojo.setPhone(busiUpdatePhoneModel.getPhone());

        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isEmpty(busiCustomerPojo)){
            throw new BusinessException("BC0054",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        BusiCustomerPojo updateCustomerPojo = new BusiCustomerPojo();
        updateCustomerPojo.setId(busiCustomerPojo.getId());
        updateCustomerPojo.setPhone(busiUpdatePhoneModel.getNewPhone());
        int result = customerBusiServiceImpl.updateByPrimaryKeySelective(updateCustomerPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);

        redisManager.deleteValue(busiUpdatePhoneModel.getNewPhone()+CustomerConstant.updPhoneSufixKey);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiUpdatePhoneModel busiUpdatePhoneModel = (BusiUpdatePhoneModel)model;

        /**
         * 校验 token
         */
        if(CommonUtil.isEmpty(busiUpdatePhoneModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiUpdatePhoneModel.getId())){
            errorMessage.rejectNull("id",null,"用户id");
            return errorMessage;
        }


        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiUpdatePhoneModel.getToken());
        if(tokenUser.getId().intValue() != busiUpdatePhoneModel.getId().intValue()){
            LoggerUtil.error(UpdateCustomerPhoneExecutor.class,"tokenId:"+tokenUser.getId()+".....param CustomerId:"+busiUpdatePhoneModel.getId());
            errorMessage.rejectNull("info",null,"提交参数异常");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiUpdatePhoneModel.getNewPhone())){
            errorMessage.rejectNull("phone",null,"phone");
            return errorMessage;
        }

        /**
         * 校验手机号码格式
         */
        if(!busiUpdatePhoneModel.getNewPhone().matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("phone","BC0017","手机号");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiUpdatePhoneModel.getSmsCode())){
            errorMessage.rejectNull("smsCode",null,"smsCode");
            return errorMessage;
        }

        Object cacheCode = redisManager.getValue(busiUpdatePhoneModel.getNewPhone()+CustomerConstant.updPhoneSufixKey);
        if(cacheCode == null){
            errorMessage.rejectError("smsCode","BC0029","");
            return errorMessage;
        }else if(!cacheCode.toString().equals(busiUpdatePhoneModel.getSmsCode())){
            errorMessage.rejectError("smsCode","BC0053","");
            return errorMessage;
        }
        return errorMessage;
    }
}
