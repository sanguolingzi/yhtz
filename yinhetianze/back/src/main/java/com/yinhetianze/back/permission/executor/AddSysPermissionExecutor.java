package com.yinhetianze.back.permission.executor;

import com.yinhetianze.pojo.back.BusiSysPermissionPojo;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.permission.service.busi.SysPermissionBusiService;
import com.yinhetianze.systemservice.permission.service.info.SysPermissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增系统权限
 */
@Component
public class AddSysPermissionExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysPermissionBusiService sysPermissionBusiServiceImpl;

    @Autowired
    private SysPermissionInfoService sysPermissionInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysPermissionModel busiSysPermissionModel = (BusiSysPermissionModel)model;
        int result = sysPermissionBusiServiceImpl.addInfo(busiSysPermissionModel);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysPermissionModel busiSysPermissionModel = (BusiSysPermissionModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysPermissionModel.getPermName())){
            errorMessage.rejectNull("permName",null,"权限名称");
            return errorMessage;
        }

        BusiSysPermissionPojo sysPermissionPojo = new BusiSysPermissionPojo();
        sysPermissionPojo.setPermName(busiSysPermissionModel.getPermName());
        sysPermissionPojo = sysPermissionInfoServiceImpl.selectOne(sysPermissionPojo);
        if(sysPermissionPojo!=null){
            errorMessage.rejectError("perName","BC0055","权限名称","权限名称");
            return errorMessage;
        }

        return errorMessage;
    }
}
