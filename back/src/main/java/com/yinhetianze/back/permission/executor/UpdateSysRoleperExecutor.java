package com.yinhetianze.back.permission.executor;

import com.yinhetianze.systemservice.permission.model.BusiSysRoleperModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.permission.service.busi.SysRoleperBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改系统权限菜单
 */
@Component
public class UpdateSysRoleperExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysRoleperBusiService sysRoleperBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysRoleperModel busiSysRoleperModel = (BusiSysRoleperModel)model;
        int result = sysRoleperBusiServiceImpl.updateInfo(busiSysRoleperModel);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysRoleperModel busiSysRoleperModel = (BusiSysRoleperModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysRoleperModel.getRoleId())){
            errorMessage.rejectNull("roleId",null,"角色Id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysRoleperModel.getPermissionIds())){
           errorMessage.rejectNull("permissionIds",null,"权限");
           return errorMessage;
        }
        return errorMessage;
    }
}
