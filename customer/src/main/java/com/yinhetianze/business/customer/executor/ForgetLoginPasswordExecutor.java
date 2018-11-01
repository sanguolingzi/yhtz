package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiUpdatePasswordModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.business.customer.util.CustomerUtil;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 忘记密码
 */

@Component
public class ForgetLoginPasswordExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private RedisManager redisManager;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiUpdatePasswordModel busiUpdatePasswordModel = (BusiUpdatePasswordModel)model;
        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByPhone(busiUpdatePasswordModel.getPhone());
        busiCustomerPojo.setLoginPassword(CustomerUtil.createPassword(busiUpdatePasswordModel.getNewPassword()));
        int result = customerBusiServiceImpl.updateByPrimaryKeySelective(busiCustomerPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);

        redisManager.deleteValue(busiUpdatePasswordModel.getPhone()+CustomerConstant.currentUserOperator);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiUpdatePasswordModel busiUpdatePasswordModel = (BusiUpdatePasswordModel)model;

        /**
         * 校验 phone
         */
        String phone = busiUpdatePasswordModel.getPhone();
        if(CommonUtil.isEmpty(phone)){
            errorMessage.rejectNull("phone",null,"phone");
            return errorMessage;
        }

        if(!phone.matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("phone","BC0017","");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiUpdatePasswordModel.getNewPassword())){
            errorMessage.rejectNull("password",null,"新密码");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiUpdatePasswordModel.getConfirmPassword())){
            errorMessage.rejectNull("password",null,"确认新密码");
            return errorMessage;
        }

        /**
         * 校验 新密码和确认密码是否相同
         */
        if(!busiUpdatePasswordModel.getNewPassword().equalsIgnoreCase(busiUpdatePasswordModel.getConfirmPassword())){
            errorMessage.rejectError("password","BC0027");
            return errorMessage;
        }


        Object value = redisManager.getValue(phone+CustomerConstant.currentUserOperator);
        if(CommonUtil.isEmpty(value)){
            errorMessage.rejectErrorMessage("info","操作超时!","操作超时!");
            return errorMessage;
        }

        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByPhone(phone);
        if(busiCustomerPojo == null){
            errorMessage.rejectErrorMessage("info","用户不存在!","用户不存在!");
            return errorMessage;
        }

        return errorMessage;
    }
}
