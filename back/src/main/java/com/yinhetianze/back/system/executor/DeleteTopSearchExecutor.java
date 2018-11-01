package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.DeleteModel;
import com.yinhetianze.systemservice.system.service.busi.TopSearchBusiService;
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
 * 热搜词 删除信息
 */

@Component
public class DeleteTopSearchExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private TopSearchBusiService topSearchBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel topSearchModel = (DeleteModel)model;
        int result = topSearchBusiServiceImpl.batchDelete(topSearchModel.getIds());
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        DeleteModel topSearchModel = (DeleteModel)model;
        if(topSearchModel.getIds() == null){
            errorMessage.rejectNull("ids",null,"ids");
            return errorMessage;
        }

        return errorMessage;
    }

}
