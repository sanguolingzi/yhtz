package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.business.customer.model.BusiCustomerFeedbackPageModel;

import java.util.List;

public interface CustomerFeedbackInfoService
{
    BusiCustomerFeedbackModel selectFeedback(Integer feedbackId);

    List<BusiCustomerFeedbackModel> selectList(BusiCustomerFeedbackPageModel busiCustomerFeedbackPageModel);

    List<BusiCustomerFeedbackModel> selectListForManage(BusiCustomerFeedbackPageModel busiCustomerFeedbackPageModel);
}