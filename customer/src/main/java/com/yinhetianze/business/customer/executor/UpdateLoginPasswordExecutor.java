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
 * 消费者/会员 修改登录密码
 */

@Component
public class UpdateLoginPasswordExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        BusiUpdatePasswordModel busiUpdatePasswordModel = (BusiUpdatePasswordModel)model;
        busiCustomerPojo.setId(busiUpdatePasswordModel.getCustomerId());
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
         * 校验 customerId
         */
        if(CommonUtil.isEmpty(busiUpdatePasswordModel.getToken())){
            errorMessage.rejectNull("token",null,"用户信息");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiUpdatePasswordModel.getCustomerId())){
            errorMessage.rejectNull("customerId",null,"用户id");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiUpdatePasswordModel.getToken());

        if(tokenUser.getId().intValue() != busiUpdatePasswordModel.getCustomerId().intValue()){
            LoggerUtil.error(UpdateLoginPasswordExecutor.class,"tokenId:"+tokenUser.getId()+".....param CustomerId:"+busiUpdatePasswordModel.getCustomerId());
            errorMessage.rejectNull("info",null,"提交参数异常");
            return errorMessage;
        }


        BusiCustomerPojo busiCustomerPojo  = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);

        if(busiCustomerPojo == null){
            errorMessage.rejectError("info","BC0009");
            return errorMessage;
        }

        Object value = redisManager.getValue(busiCustomerPojo.getPhone()+ CustomerConstant.currentUserOperator);
        if(CommonUtil.isEmpty(value)){
            errorMessage.rejectErrorMessage("info","操作超时!","操作超时!");
            return errorMessage;
        }

        busiUpdatePasswordModel.setPhone(busiCustomerPojo.getPhone());
        busiUpdatePasswordModel.setCustomerId(busiCustomerPojo.getId());

        //查询该用户是否有登录密码  若有 则默认为修改密码 进行修改密码得逻辑校验 否则 认为是设置新密码
        if(CommonUtil.isNotEmpty(busiCustomerPojo.getLoginPassword())){
            if(CommonUtil.isEmpty(busiUpdatePasswordModel.getLoginPassword())){
                errorMessage.rejectNull("password",null,"原密码");
                return errorMessage;
            }


            /**
             * 输入原密码是否与数据库密码相同
             */
            if(!CustomerUtil.checkPassword(busiCustomerPojo.getLoginPassword(),busiUpdatePasswordModel.getLoginPassword())){
                errorMessage.rejectError("password","BC0016");
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
             * 校验新密码是否与原密码相同
             */
            if(busiUpdatePasswordModel.getLoginPassword().equalsIgnoreCase(busiUpdatePasswordModel.getNewPassword())){
                errorMessage.rejectError("password","BC0039");
                return errorMessage;
            }

            /**
             * 校验 新密码和确认密码是否相同
             */
            if(!busiUpdatePasswordModel.getNewPassword().equalsIgnoreCase(busiUpdatePasswordModel.getConfirmPassword())){
                errorMessage.rejectError("password","BC0027");
                return errorMessage;
            }
        }else{//新增登录密码

            if(CommonUtil.isEmpty(busiUpdatePasswordModel.getNewPassword())){
                errorMessage.rejectNull("password",null,"新密码");
                return errorMessage;
            }

            if(CommonUtil.isEmpty(busiUpdatePasswordModel.getConfirmPassword())){
                errorMessage.rejectNull("password",null,"确认新密码");
                return errorMessage;
            }

            /**
             * 校验新密码是否与原密码相同
             */
            /*
            if(busiUpdatePasswordModel.getLoginPassword().equalsIgnoreCase(busiUpdatePasswordModel.getNewPassword())){
                errorMessage.rejectError("password","BC0039");
                return errorMessage;
            }
            */

            /**
             * 校验 新密码和确认密码是否相同
             */
            if(!busiUpdatePasswordModel.getNewPassword().equalsIgnoreCase(busiUpdatePasswordModel.getConfirmPassword())){
                errorMessage.rejectError("password","BC0027");
                return errorMessage;
            }
        }
        return errorMessage;
    }
}
