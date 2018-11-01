package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 修改账户会员状态
 */

@Component
public class UpdateCustomerMemberInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        BeanUtils.copyProperties(model,busiCustomerPojo);

        int result = 0;
        if(busiCustomerPojo.getIsMember() == 0){
            result = customerBusiServiceImpl.updateMember(busiCustomerPojo.getId());
        }else if(busiCustomerPojo.getIsMember() == 1){
            result = customerBusiServiceImpl.cancelMember(busiCustomerPojo.getId());
        }

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
            errorMessage.rejectErrorMessage("info","modelName不正确","modelName不正确");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerModel.getId())){
            errorMessage.rejectNull("id",null,"用户id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerModel.getIsMember())){
            errorMessage.rejectNull("isMember",null,"isMember");
            return errorMessage;
        }
        return errorMessage;
    }
}
