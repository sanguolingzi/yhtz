package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.SysDictionaryModel;
import com.yinhetianze.systemservice.system.service.busi.SysDictionaryBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.SysDictionaryPojo;
import com.yinhetianze.systemservice.system.service.info.SysDictionaryInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改错误码
 */

@Component
public class UpdateSysDictionaryExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysDictionaryBusiService sysDictionaryBusiServiceImpl;

    @Autowired
    private SysDictionaryInfoService sysDictionaryInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysDictionaryPojo sysDictionaryPojo = new SysDictionaryPojo();
        BeanUtils.copyProperties(model,sysDictionaryPojo);
        int result = sysDictionaryBusiServiceImpl.updateByPrimaryKeySelective(sysDictionaryPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysDictionaryModel sysDictionaryModel = (SysDictionaryModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        SysDictionaryPojo sysDictionaryPojo=new SysDictionaryPojo();
        sysDictionaryPojo.setDicName(sysDictionaryModel.getDicName());
        sysDictionaryPojo.setDelFlag((short)0);
        sysDictionaryPojo=sysDictionaryInfoServiceImpl.selectSysDictionary(sysDictionaryPojo);
       if(sysDictionaryPojo != null
               && sysDictionaryPojo.getId() != sysDictionaryModel.getId()){
           errorMessage.rejectError("pName","BC0055","字典名称" ,"字典名称");
           return errorMessage;
       }
       if(sysDictionaryModel.getId() == null){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
       }
       return errorMessage;
    }
}
