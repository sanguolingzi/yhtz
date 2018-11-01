package com.yinhetianze.back.menu.executor;

import com.yinhetianze.systemservice.menu.model.BusiSysPerMenuModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.menu.service.busi.SysPerMenuBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增系统权限菜单
 */
@Component
public class AddSysPerMenuExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysPerMenuBusiService sysPerMenuBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysPerMenuModel busiSysPerMenuModel = (BusiSysPerMenuModel)model;
        int result = sysPerMenuBusiServiceImpl.addInfo(busiSysPerMenuModel);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysPerMenuModel busiSysPerMenuModel = (BusiSysPerMenuModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysPerMenuModel.getPermissionId())){
            errorMessage.rejectNull("permissionId",null,"权限Id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysPerMenuModel.getMenuIds())){
           errorMessage.rejectNull("menuIds",null,"菜单");
           return errorMessage;
        }
        return errorMessage;
    }
}
