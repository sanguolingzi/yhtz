package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.DeleteModel;
import com.yinhetianze.systemservice.system.service.busi.SysMenuBusiService;
import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.SysMenuPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 删除菜单
 */

@Component
public class DeleteSysMenuExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private SysMenuBusiService sysMenuBusiServiceImpl;

    @Autowired
    private SysMenuInfoService sysMenuInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel deleteModel = (DeleteModel)model;
        int result = sysMenuBusiServiceImpl.deleteBatch(deleteModel.getIds());
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        DeleteModel deleteModel = (DeleteModel)model;

        if(CommonUtil.isEmpty(deleteModel.getIds())){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }

        SysMenuPojo sysMenuPojo = new SysMenuPojo();
        sysMenuPojo.setParentId(Integer.parseInt(deleteModel.getIds()));
        sysMenuPojo.setDelFlag((short)0);
        List<SysMenuPojo> list = sysMenuInfoServiceImpl.selectList(sysMenuPojo);
        if(CommonUtil.isNotEmpty(list)){
            errorMessage.rejectErrorMessage("id","该菜单有子菜单,请先删除子菜单!","该菜单有子菜单,请先删除子菜单!");
            return errorMessage;
        }
        return errorMessage;
    }
}
