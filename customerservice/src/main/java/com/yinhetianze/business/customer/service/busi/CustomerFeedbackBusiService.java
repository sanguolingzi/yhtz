package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.core.business.BusinessException;

public interface CustomerFeedbackBusiService
{
    int addInfo(BusiCustomerFeedbackModel busiCustomerFeedbackModel) throws BusinessException;

    int deleteInfo(BusiCustomerFeedbackModel busiCustomerFeedbackModel) throws BusinessException;

    int deleteInfoBatch(String ids) throws BusinessException;
}