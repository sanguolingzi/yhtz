package com.yinhetianze.back.system.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.QuestionPojo;
import com.yinhetianze.systemservice.system.model.QuestionModel;
import com.yinhetianze.systemservice.system.service.info.QuestionInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class FindQuestionExecutor  extends AbstractRestBusiExecutor{

    @Autowired
    private QuestionInfoService questionInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        QuestionPojo questionPojo=new QuestionPojo();
        BeanUtils.copyProperties(model,questionPojo);
        List<QuestionPojo> list=questionInfoServiceImpl.selectQuestion(questionPojo);
        return list;
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        QuestionModel questionModel=(QuestionModel)model;
        if(CommonUtil.isEmpty(questionModel.getClassifyId())){
            errorMessage.rejectNull("classifyId",null,"分类ID");
        }
        return super.validate(request, model, params);
    }
}
