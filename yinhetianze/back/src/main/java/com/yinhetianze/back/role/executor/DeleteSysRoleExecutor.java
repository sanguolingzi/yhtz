package com.yinhetianze.back.role.executor;

import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.role.service.busi.SysRoleBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除系统角色
 */
@Component
public class DeleteSysRoleExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysRoleBusiService sysRoleBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysRoleModel busiSysRoleModel = (BusiSysRoleModel)model;
        int result = sysRoleBusiServiceImpl.deleteInfo(busiSysRoleModel.getId());
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysRoleModel busiSysRoleModel = (BusiSysRoleModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysRoleModel.getId())){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
        }
        return errorMessage;
    }
}
