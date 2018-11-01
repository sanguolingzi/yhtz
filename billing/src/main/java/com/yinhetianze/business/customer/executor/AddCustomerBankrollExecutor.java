package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 新增账户
 */

@Component
public class AddCustomerBankrollExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = new BusiCustomerBankrollPojo();
        BeanUtils.copyProperties(model,busiCustomerBankrollPojo);
        int result = customerBankrollBusiServiceImpl.add(busiCustomerBankrollPojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        return errorMessage;
    }

}
