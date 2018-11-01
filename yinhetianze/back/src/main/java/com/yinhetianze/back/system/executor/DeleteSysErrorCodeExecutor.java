package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.SysErrorCodeModel;
import com.yinhetianze.systemservice.system.service.busi.SysErrorCodeBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.SysErrorCodePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 删除信息
 */

@Component
public class DeleteSysErrorCodeExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private SysErrorCodeBusiService sysErrorCodeBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysErrorCodeModel sysErrorCodeModel = (SysErrorCodeModel)model;
        SysErrorCodePojo sysErrorCodePojo = new SysErrorCodePojo();
        sysErrorCodePojo.setErrorCode(sysErrorCodeModel.getErrorCode());
        //sysErrorCodePojo.setDelFlag((short)1);
        int result = sysErrorCodeBusiServiceImpl.deleteSelective(sysErrorCodePojo);
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        SysErrorCodeModel sysErrorCodeModel = (SysErrorCodeModel)model;
        if(sysErrorCodeModel.getErrorCode() == null){
            errorMessage.rejectNull("errorCode",null,"errorCode");
            return errorMessage;
        }

        return errorMessage;
    }

}
