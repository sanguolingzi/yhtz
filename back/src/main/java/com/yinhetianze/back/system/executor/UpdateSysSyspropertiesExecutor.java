package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.BusiSysSyspropertiesModel;
import com.yinhetianze.systemservice.system.service.busi.SysSyspropertiesService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.systemservice.system.service.info.SysSyspropertiesInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 修改系统参数
 */

@Component
public class UpdateSysSyspropertiesExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysSyspropertiesService sysSyspropertiesServiceImpl;

    @Autowired
    private SysSyspropertiesInfoService sysSyspropertiesInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysSyspropertiesPojo  busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
        BeanUtils.copyProperties(model,busiSysSyspropertiesPojo);
        int result = sysSyspropertiesServiceImpl.updateByPrimaryKeySelective(busiSysSyspropertiesPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysSyspropertiesModel busiSysSyspropertiesModel = (BusiSysSyspropertiesModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
       BusiSysSyspropertiesPojo  busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
       busiSysSyspropertiesPojo.setpName(busiSysSyspropertiesModel.getpName());
       busiSysSyspropertiesPojo.setDelFlag((short)0);
       busiSysSyspropertiesPojo=sysSyspropertiesInfoServiceImpl.sysProperties(busiSysSyspropertiesPojo);

       if(busiSysSyspropertiesPojo != null
               && busiSysSyspropertiesPojo.getId().intValue() != busiSysSyspropertiesModel.getId().intValue()){
           errorMessage.rejectError("pName","BC0055","系统参数","系统参数");
           return errorMessage;
       }
       if(busiSysSyspropertiesModel.getId() == null){
           errorMessage.rejectNull("Id",null,"Id");
           return errorMessage;
       }

       if(busiSysSyspropertiesModel.getUpdateUser() == null){
           errorMessage.rejectNull("updateUser",null,"updateUser");
           return errorMessage;
       }
       return errorMessage;
    }
}
