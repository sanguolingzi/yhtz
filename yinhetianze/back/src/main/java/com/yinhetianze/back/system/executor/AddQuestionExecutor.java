package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.QuestionModel;
import com.yinhetianze.systemservice.system.service.busi.QuestionBusiService;
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
public class AddQuestionExecutor  extends AbstractRestBusiExecutor<String> {
    @Autowired
    private QuestionBusiService questionBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        QuestionPojo questionPojo=new QuestionPojo();
        BeanUtils.copyProperties(model,questionPojo);
        int result=questionBusiServiceImpl.addQuestion(questionPojo);
        if(result!=1){
            throw new BusinessException("添加问答失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        QuestionModel questionModel=(QuestionModel)model;
        if(CommonUtil.isEmpty(questionModel.getQuestionName())){
            errorMessage.rejectNull("questionName",null,"问答标题");
        }
        if(CommonUtil.isEmpty(questionModel.getQuestionAnswer())){
            errorMessage.rejectNull("questionAnswer",null,"问题答案");
        }
        if(CommonUtil.isEmpty(questionModel.getClassifyId())){
            errorMessage.rejectNull("classifyId",null,"分类ID");
        }
        if(CommonUtil.isEmpty(questionModel.getSort())){
            errorMessage.rejectNull("sort",null,"排序");
        }
        if(CommonUtil.isEmpty(questionModel.getIsShow())){
            errorMessage.rejectNull("isShow",null,"是否显示");
        }
        return errorMessage;
    }
}
