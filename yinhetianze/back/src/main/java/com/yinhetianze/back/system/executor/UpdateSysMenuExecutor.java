package com.yinhetianze.back.system.executor;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysPermissionPojo;
import com.yinhetianze.systemservice.system.model.SysMenuModel;
import com.yinhetianze.systemservice.system.service.busi.SysMenuBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.SysMenuPojo;
import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改系统菜单
 */

@Component
public class UpdateSysMenuExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysMenuBusiService sysMenuBusiServiceImpl;

    @Autowired
    private SysMenuInfoService sysMenuInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysMenuPojo sysMenuPojo = new SysMenuPojo();
        BeanUtils.copyProperties(model,sysMenuPojo);
        int result = sysMenuBusiServiceImpl.updateByPrimaryKeySelective(sysMenuPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
       SysMenuModel sysMenuModel = (SysMenuModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

       if(sysMenuModel.getId() == null){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
       }

       if(CommonUtil.isEmpty(sysMenuModel.getMenuLink())){
           errorMessage.rejectNull("menuLink",null,"菜案链接");
           return errorMessage;
       }

       if(CommonUtil.isEmpty(sysMenuModel.getParentId())){
           errorMessage.rejectNull("parentId",null,"父菜单Id");
           return errorMessage;
       }

       if(CommonUtil.isEmpty(sysMenuModel.getIsWork())){
           errorMessage.rejectNull("isWork",null,"菜单状态");
           return errorMessage;
       }

       SysMenuPojo sysMenuPojo = new SysMenuPojo();
       sysMenuPojo.setMenuName(sysMenuModel.getMenuName());
       sysMenuPojo = sysMenuInfoServiceImpl.selectOne(sysMenuPojo);
       if(sysMenuPojo != null
               && sysMenuPojo.getId().intValue() != sysMenuModel.getId().intValue() ){
           errorMessage.rejectError("menuName","BC0055","菜单名称","菜单名称");
           return errorMessage;
       }
       return errorMessage;
    }
}
