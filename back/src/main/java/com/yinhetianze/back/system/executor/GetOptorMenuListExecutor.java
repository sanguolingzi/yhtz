package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取用户用的菜单
 */

@Component
public class GetOptorMenuListExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysMenuInfoService sysMenuInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysOptorModel busiSysOptorModel = (BusiSysOptorModel)model;
        return sysMenuInfoServiceImpl.selectOptorMenuList(busiSysOptorModel.getId());
    }

    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiSysOptorModel busiSysOptorModel = (BusiSysOptorModel)model;
        if(CommonUtil.isEmpty(busiSysOptorModel.getId())){
            errorMessage.rejectNull("id",null,"用户id");
            return errorMessage;
        }
        return errorMessage;
    }
}
