package com.yinhetianze.back.system.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.ConcatenatedCodePojo;
import com.yinhetianze.systemservice.system.model.ConcatenatedCodeModel;
import com.yinhetianze.systemservice.system.service.busi.ConcatenatedCodeBusiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AddConcatenatedCodeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ConcatenatedCodeBusiService concatenatedCodeBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ConcatenatedCodeModel concatenatedCodeModel =(ConcatenatedCodeModel)model;
        ConcatenatedCodePojo concatenatedCodePojo =new ConcatenatedCodePojo();
        BeanUtils.copyProperties(concatenatedCodeModel, concatenatedCodePojo);
        int result= concatenatedCodeBusiServiceImpl.insertSelective(concatenatedCodePojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }
}
