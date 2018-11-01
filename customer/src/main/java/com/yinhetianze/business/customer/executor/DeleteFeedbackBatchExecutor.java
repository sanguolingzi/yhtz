package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.DeleteModel;
import com.yinhetianze.business.customer.service.busi.CustomerFeedbackBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 批量删除 消费者/会员 反馈
 */
@Component
public class DeleteFeedbackBatchExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private CustomerFeedbackBusiService customerFeedbackBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel deleteModel = (DeleteModel)model;
        customerFeedbackBusiServiceImpl.deleteInfoBatch(deleteModel.getIds());
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        DeleteModel deleteModel = (DeleteModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(deleteModel.getIds())){
            errorMessage.rejectNull("ids",null,"ids");
            return errorMessage;
        }
        return errorMessage;
    }
}
