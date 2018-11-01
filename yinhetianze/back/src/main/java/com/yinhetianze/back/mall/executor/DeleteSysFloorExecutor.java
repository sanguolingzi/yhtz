package com.yinhetianze.back.mall.executor;

import com.yinhetianze.systemservice.mall.service.busi.SysFloorBusiService;
import com.yinhetianze.systemservice.system.model.DeleteModel;
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
 * 删除楼层
 */

@Component
public class DeleteSysFloorExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private SysFloorBusiService sysFloorBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel deleteModel = (DeleteModel)model;
        sysFloorBusiServiceImpl.deleteBatch(deleteModel.getIds());
        return "success";
    }

    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        DeleteModel deleteModel = (DeleteModel)model;
        if(CommonUtil.isEmpty(deleteModel.getIds())){
            errorMessage.rejectNull("ids",null,"ids");
            return errorMessage;
        }
        return errorMessage;
    }
}
