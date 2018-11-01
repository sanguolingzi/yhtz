package com.yinhetianze.back.permission.executor;

import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.permission.service.busi.SysPermissionBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除系统权限
 */
@Component
public class DeleteSysPermissionExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysPermissionBusiService sysPermissionBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysPermissionModel busiSysPermissionModel = (BusiSysPermissionModel)model;
        int result = sysPermissionBusiServiceImpl.deleteInfo(busiSysPermissionModel.getId());
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysPermissionModel busiSysPermissionModel = (BusiSysPermissionModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysPermissionModel.getId())){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
        }
        return errorMessage;
    }
}
