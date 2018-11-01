package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.SysErrorCodeModel;
import com.yinhetianze.systemservice.system.service.busi.SysErrorCodeBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.SysErrorCodePojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改错误码
 */

@Component
public class UpdateSysErrorCodeExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysErrorCodeBusiService sysErrorCodeBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysErrorCodePojo sysErrorCodePojo = new SysErrorCodePojo();
        BeanUtils.copyProperties(model,sysErrorCodePojo);
        int result = sysErrorCodeBusiServiceImpl.updateByPrimaryKeySelective(sysErrorCodePojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysErrorCodeModel sysErrorCodeModel = (SysErrorCodeModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

       if(sysErrorCodeModel.getErrorCode() == null){
           errorMessage.rejectNull("errorCode",null,"errorCode");
           return errorMessage;
       }
       return errorMessage;
    }
}
