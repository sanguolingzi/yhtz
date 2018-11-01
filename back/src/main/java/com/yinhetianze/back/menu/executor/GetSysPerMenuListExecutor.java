package com.yinhetianze.back.menu.executor;

import com.yinhetianze.systemservice.menu.model.BusiSysPerMenuModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysPerMenuPojo;
import com.yinhetianze.systemservice.menu.service.info.SysPerMenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询权限拥有菜单
 */
@Component
public class GetSysPerMenuListExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysPerMenuInfoService sysPerMenuInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysPerMenuModel busiSysPerMenuModel = (BusiSysPerMenuModel)model;
        BusiSysPerMenuPojo busiSysPerMenuPojo = new BusiSysPerMenuPojo();
        busiSysPerMenuPojo.setPermissionId(busiSysPerMenuModel.getPermissionId());
        return sysPerMenuInfoServiceImpl.selectList(busiSysPerMenuPojo);
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysPerMenuModel busiSysPerMenuModel = (BusiSysPerMenuModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysPerMenuModel.getPermissionId())){
            errorMessage.rejectNull("permissionId",null,"权限id");
            return errorMessage;
        }
        return errorMessage;
    }
}
