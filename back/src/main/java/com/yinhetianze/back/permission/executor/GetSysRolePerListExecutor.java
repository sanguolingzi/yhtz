package com.yinhetianze.back.permission.executor;

import com.yinhetianze.systemservice.permission.model.BusiSysRoleperModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysRoleperPojo;
import com.yinhetianze.systemservice.permission.service.info.SysRoleperInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询角色拥有的权限关系
 */
@Component
public class GetSysRolePerListExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysRoleperInfoService sysRoleperInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysRoleperModel busiSysRoleperModel = (BusiSysRoleperModel)model;
        BusiSysRoleperPojo busiSysRoleperPojo = new BusiSysRoleperPojo();
        busiSysRoleperPojo.setRoleId(busiSysRoleperModel.getRoleId());
        List<BusiSysRoleperPojo> list = sysRoleperInfoServiceImpl.select(busiSysRoleperPojo);
        return list;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysRoleperModel busiSysRoleperModel = (BusiSysRoleperModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysRoleperModel.getRoleId())){
            errorMessage.rejectNull("roleId",null,"角色id");
            return errorMessage;
        }
        return errorMessage;
    }
}
