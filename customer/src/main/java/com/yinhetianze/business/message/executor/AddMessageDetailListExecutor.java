package com.yinhetianze.business.message.executor;

import com.yinhetianze.business.message.model.BusiMessageDetailListModel;
import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增消息详情信息(集合)
 */

@Component
public class AddMessageDetailListExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private MessageDetailBusiService messageDetailBusiServiceImpl;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiMessageDetailListModel busiMessageDetailListModel = (BusiMessageDetailListModel)model;
        int result = messageDetailBusiServiceImpl.addListInfo(busiMessageDetailListModel);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiMessageDetailListModel busiMessageDetailListModel = (BusiMessageDetailListModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        return errorMessage;
    }
}
