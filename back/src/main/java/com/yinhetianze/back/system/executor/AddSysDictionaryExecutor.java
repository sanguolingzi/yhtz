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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增系统参数
 */
@Component
public class AddSysDictionaryExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysDictionaryBusiService sysDictionaryBusiServiceImpl;
    @Autowired
    private SysDictionaryInfoService sysDictionaryInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysDictionaryPojo sysDictionaryPojo = new SysDictionaryPojo();
        BeanUtils.copyProperties(model,sysDictionaryPojo);
        int result = sysDictionaryBusiServiceImpl.insertSelective(sysDictionaryPojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysDictionaryModel sysDictionaryModel = (SysDictionaryModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(sysDictionaryInfoServiceImpl.selectSysDictionaryId(sysDictionaryModel)>0){
            errorMessage.rejectError("pName","BC0055","字典名称" ,"字典名称");
            return errorMessage;
        }

        if(sysDictionaryModel.getDicName() == null){
            errorMessage.rejectNull("dicName",null,"字典名称");
            return errorMessage;
        }
        if(sysDictionaryModel.getDicValue() == null){
           errorMessage.rejectNull("dicValue",null,"字典值");
           return errorMessage;
        }
        if(sysDictionaryModel.getDicType() == null){
           errorMessage.rejectNull("dicType",null,"字典类型");
           return errorMessage;
        }
        if(sysDictionaryModel.getCreateUser() == null){
           errorMessage.rejectNull("createUser",null,"用户ID");
           return errorMessage;
        }



        return errorMessage;
    }
}
