package com.yinhetianze.back.mall.executor;

import com.yinhetianze.systemservice.mall.model.MallFlashreportModel;
import com.yinhetianze.systemservice.mall.service.busi.MallFlashreportBusiService;
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
 * 修改商品快报
 */
@Component
public class UpdateMallFlashreportExecutor extends AbstractRestBusiExecutor<String>{

    @Autowired
    private MallFlashreportBusiService mallFlashreportBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result= mallFlashreportBusiServiceImpl.updateInfo((MallFlashreportModel)model);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        MallFlashreportModel mallFlashreportModel=(MallFlashreportModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(mallFlashreportModel.getId()== null ){
            errorMessage.rejectNull("id",null,"id");
            return  errorMessage;
        }
        return errorMessage;
    }
}
