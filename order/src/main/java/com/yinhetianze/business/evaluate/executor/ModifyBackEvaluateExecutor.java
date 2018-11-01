package com.yinhetianze.business.evaluate.executor;

import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.evaluate.service.EvaluateBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ModifyBackEvaluateExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private EvaluateBusiService evaluateBusiServiceImpl;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        EvaluateModel evaluateModel=(EvaluateModel)model;
        int i=evaluateBusiServiceImpl.modifyEvaluate(evaluateModel.getEvaluateIds());
        if(i<1){
            throw new BusinessException("更新评价状态失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        EvaluateModel evaluateModel=(EvaluateModel)model;
        if(CommonUtil.isEmpty(evaluateModel.getEvaluateIds())){
            error.rejectNull("evaluateIds",null,"评价的ID数组");
        }
        return super.validate(request, model, params);
    }
}
