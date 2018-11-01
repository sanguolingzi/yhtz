package com.yinhetianze.back.mall.executor;

import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.systemservice.mall.service.busi.MallActivityBusiService;
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
 * 修改商城公告
 */

@Component
public class UpdateMallActivityExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private MallActivityBusiService mallActivityBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = mallActivityBusiServiceImpl.updateInfo((MallActivityModel)model);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
       MallActivityModel mallActivityModel = (MallActivityModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

       if(mallActivityModel.getId() == null){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
       }
       return errorMessage;
    }
}
