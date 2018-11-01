package com.yinhetianze.back.user.executor;

import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.user.service.busi.SysOptorBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除系统用户
 */
@Component
public class DeleteSysOptorExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysOptorBusiService sysOptorBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysOptorModel busiSysOptorModel = (BusiSysOptorModel)model;
        int result = sysOptorBusiServiceImpl.deleteInfo(busiSysOptorModel.getId());
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysOptorModel busiSysOptorModel = (BusiSysOptorModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysOptorModel.getId())){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
        }
        return errorMessage;
    }
}
