package com.yinhetianze.back.role.executor;

import com.yinhetianze.pojo.back.BusiSysRolePojo;
import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.role.service.busi.SysRoleBusiService;
import com.yinhetianze.systemservice.role.service.info.SysRoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增系统角色
 */
@Component
public class AddSysRoleExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysRoleBusiService sysRoleBusiServiceImpl;

    @Autowired
    private SysRoleInfoService sysRoleInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysRoleModel busiSysRoleModel = (BusiSysRoleModel)model;
        int result = sysRoleBusiServiceImpl.addInfo(busiSysRoleModel);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysRoleModel busiSysRoleModel = (BusiSysRoleModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysRoleModel.getRoleName())){
            errorMessage.rejectNull("roleName",null,"角色名称");
            return errorMessage;
        }

        BusiSysRolePojo busiSysRolePojo = new BusiSysRolePojo();
        busiSysRolePojo.setRoleName(busiSysRoleModel.getRoleName());
        busiSysRolePojo = sysRoleInfoServiceImpl.selectOne(busiSysRolePojo);
        if(busiSysRolePojo != null){
            errorMessage.rejectError("roleName","BC0055","角色名称","角色名称");
            return errorMessage;
        }

        return errorMessage;
    }
}
