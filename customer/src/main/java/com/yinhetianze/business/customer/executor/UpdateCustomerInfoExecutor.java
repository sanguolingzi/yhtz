package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
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
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 修改基础信息
 */

@Component
public class UpdateCustomerInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        BeanUtils.copyProperties(model,busiCustomerPojo);
        int result = customerBusiServiceImpl.updateByPrimaryKeySelective(busiCustomerPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        /**
         * 校验 token
         */
        if(CommonUtil.isEmpty(busiCustomerModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }


        //这里后台操作用户修改 需要传递指定参数才设置id
        if(CommonUtil.isEmpty(busiCustomerModel.getModelName())
            || !"backSendRequest".equals(busiCustomerModel.getModelName())){
            TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerModel.getToken());

            if(CommonUtil.isEmpty(busiCustomerModel.getId())){
                errorMessage.rejectNull("id",null,"用户id");
                return errorMessage;
            }

            if(tokenUser.getId().intValue() != busiCustomerModel.getId().intValue()){
                LoggerUtil.error(UpdateCustomerInfoExecutor.class,"tokenId:"+tokenUser.getId()+".....param CustomerId:"+busiCustomerModel.getId());
                errorMessage.rejectNull("info",null,"提交参数异常");
                return errorMessage;
            }
        }

        /**
         * 校验手机号码格式
         */
        /*
        if(CommonUtil.isNotEmpty(busiCustomerModel.getPhone())){
            if(!busiCustomerModel.getPhone().matches(CustomerConstant.phoneRegex)){
                errorMessage.rejectError("recommendPhone","BC0017","手机号");
                return errorMessage;
            }
        }
        */

        /**
         * 校验 昵称 格式 为 汉字数字字母组合   长度小于10
         */
        if(CommonUtil.isNotEmpty(busiCustomerModel.getNickName())){

            if(!busiCustomerModel.getNickName().matches(CustomerConstant.nameRegex)){
                errorMessage.rejectError("nickName","BC0032","昵称","昵称");
                return errorMessage;
            }
            errorMessage.rejectMaxLength("nickName",busiCustomerModel.getNickName(),"昵称",10);
            if(errorMessage.hasError())
                return errorMessage;


            BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
            busiCustomerPojo.setNickName(busiCustomerModel.getNickName());

            busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
            if(busiCustomerPojo != null
                    && !busiCustomerPojo.getId().equals(busiCustomerModel.getId())){
                errorMessage.rejectErrorMessage("nickName","昵称已存在!","昵称已存在!");
                return errorMessage;
            }
        }

        /**
         * 校验 性别 值 只能为 0  或者 1
         */
        if(CommonUtil.isNotEmpty(busiCustomerModel.getSex())){
                if(busiCustomerModel.getSex() != 1 && busiCustomerModel.getSex() != 0 && busiCustomerModel.getSex() != 2){
                    errorMessage.rejectError("sex","BC0032","性别","性别");
                    return errorMessage;
                }
        }

        /**
         * 校验 邮箱格式
         */
        if(CommonUtil.isNotEmpty(busiCustomerModel.getEmail())){
            errorMessage.rejectEmail("email",busiCustomerModel.getEmail());
        }
        return errorMessage;
    }
}
