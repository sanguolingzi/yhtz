package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UpdateCustomerRechargeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        return customerBankrollBusiServiceImpl.updateBankrollForOrder((BusiUpdateBankrollModel)model);
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiUpdateBankrollModel busiUpdateBankrollModel = (BusiUpdateBankrollModel)model;
        if(CommonUtil.isEmpty(busiUpdateBankrollModel.getList())){
            errorMessage.rejectNull("list",null,"list","数据集合");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiUpdateBankrollModel.getCustomerId())){
            errorMessage.rejectNull("customerId",null,"customerId","消费者id");
            return errorMessage;
        }
        return errorMessage;
    }

}
