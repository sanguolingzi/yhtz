package com.yinhetianze.business.evaluate.executor;

import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.evaluate.service.EvaluateBusiService;
import com.yinhetianze.business.evaluate.service.EvaluateInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.EvaluatePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerEvaluateExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private EvaluateBusiService evaluateBusiServiceImpl;

    @Autowired
    private EvaluateInfoService evaluateInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        EvaluateModel evaluateModel=(EvaluateModel)model;
        Map<String,Object> parameter=new HashMap<>();
        parameter.put("evaluateId",evaluateModel.getEvaluateId());
        List<EvaluateModel> list=evaluateInfoServiceImpl.findEvaluate(parameter);
        if(CommonUtil.isEmpty(list)){
            throw new BusinessException("传入的评价ID有误");
        }
        if(CommonUtil.isNotEmpty(list.get(0).getAnswer())){
            throw new BusinessException("该评论已回复过，不能重复回复");
        }
        parameter.put("answer",evaluateModel.getAnswer());
        parameter.put("answerTime",new Date());
        int i=evaluateBusiServiceImpl.updateEvaluate(parameter);
        if(i!=1){
            throw new BusinessException("增加评价回答失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        EvaluateModel evaluateModel=(EvaluateModel)model;
        if(CommonUtil.isEmpty(evaluateModel.getEvaluateId())){
            error.rejectNull("evaluateId",null,"评价的ID");
        }
        if(CommonUtil.isEmpty(evaluateModel.getAnswer())){
            error.rejectNull("answer",null,"回复的内容");
        }
        return super.validate(request, model, params);
    }
}
