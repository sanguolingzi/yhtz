package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerDrawrecordBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改用户提现记录
 */

@Component
public class UpdateCustomerDrawRecordExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerDrawrecordBusiService customerDrawrecordBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerDrawrecordModel busiCustomerDrawrecordModel = (BusiCustomerDrawrecordModel)model;
        int result =  customerDrawrecordBusiServiceImpl.updateCustomerDrawrecordInfo(busiCustomerDrawrecordModel);
        if(result == 0){
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerDrawrecordModel busiCustomerDrawrecordModel = (BusiCustomerDrawrecordModel)model;


        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getId())){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }


        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getAuditStatus())){
            errorMessage.rejectNull("auditStatus",null,"auditStatus","审核状态");
            return errorMessage;
        }

        if(busiCustomerDrawrecordModel.getAuditStatus() == 1){
            if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getReason())){
                errorMessage.rejectNull("reason",null,"reason","审核原因");
                return errorMessage;
            }
        }
        return errorMessage;
    }

}
