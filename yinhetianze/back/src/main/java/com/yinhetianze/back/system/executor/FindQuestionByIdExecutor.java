package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.QuestionModel;
import com.yinhetianze.systemservice.system.service.info.QuestionInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.QuestionPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class FindQuestionByIdExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private QuestionInfoService questionInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        QuestionPojo questionPojo=new QuestionPojo();
        BeanUtils.copyProperties(model,questionPojo);
        questionPojo=questionInfoServiceImpl.findQuestion(questionPojo);
        if(CommonUtil.isEmpty(questionPojo)){
            throw new BusinessException("传入的问答ID有误");
        }
        return questionPojo;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        QuestionModel questionModel=(QuestionModel)model;
        if(CommonUtil.isEmpty(questionModel.getId())){
            errorMessage.rejectNull("id",null,"问答ID");
        }
        return super.validate(request, model, params);
    }
}
