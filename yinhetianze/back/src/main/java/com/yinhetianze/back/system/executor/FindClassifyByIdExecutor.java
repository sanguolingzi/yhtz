package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.ClassifyModel;
import com.yinhetianze.systemservice.system.service.info.ClassifyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.ClassifyPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class FindClassifyByIdExecutor extends AbstractRestBusiExecutor<Object> {
    @Autowired
    private ClassifyInfoService classifyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ClassifyPojo classifyPojo=new ClassifyPojo();
        BeanUtils.copyProperties(model,classifyPojo);
        classifyPojo=classifyInfoServiceImpl.selectOne(classifyPojo);
        if(CommonUtil.isEmpty(classifyPojo)){
            throw new BusinessException("传入的分类ID有误");
        }
        return classifyPojo;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        ClassifyModel classifyModel=(ClassifyModel)model;
        if(CommonUtil.isEmpty(classifyModel.getId())){
            errorMessage.rejectNull("id",null,"分类ID");
        }
        return errorMessage;
    }
}
