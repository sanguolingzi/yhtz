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
 * 新增系统参数
 */
@Component
public class AddSysErrorCodeExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysErrorCodeBusiService sysErrorCodeBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysErrorCodePojo sysErrorCodePojo = new SysErrorCodePojo();
        BeanUtils.copyProperties(model,sysErrorCodePojo);
        int result = sysErrorCodeBusiServiceImpl.insertSelective(sysErrorCodePojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysErrorCodeModel sysErrorCodeModel = (SysErrorCodeModel)model;
        ErrorMessage errorMessage = new ErrorMessage();


        if(sysErrorCodeModel.getErrorCode() == null){
            errorMessage.rejectNull("errorCode",null,"错误码");
            return errorMessage;
        }
        if(sysErrorCodeModel.getErrorMessage() == null){
           errorMessage.rejectNull("errorMessage",null,"错误提示信息不能为空");
           return errorMessage;
       }

        return errorMessage;
    }
}
