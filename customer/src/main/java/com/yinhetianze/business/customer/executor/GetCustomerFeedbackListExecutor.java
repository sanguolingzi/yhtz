package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.business.customer.model.BusiCustomerFeedbackPageModel;
import com.yinhetianze.business.customer.service.info.CustomerFeedbackInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 获取反馈列表
 */
@Component
public class GetCustomerFeedbackListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerFeedbackInfoService customerFeedbackInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerFeedbackPageModel busiCustomerFeedbackPageModel = (BusiCustomerFeedbackPageModel)model;
        PageHelper.startPage(busiCustomerFeedbackPageModel.getCurrentPage(),busiCustomerFeedbackPageModel.getPageSize());
        PageInfo<BusiCustomerFeedbackModel> pageInfo = new PageInfo<BusiCustomerFeedbackModel>(
                customerFeedbackInfoServiceImpl.selectList(busiCustomerFeedbackPageModel)
        );
        return  pageInfo;
    }
}
