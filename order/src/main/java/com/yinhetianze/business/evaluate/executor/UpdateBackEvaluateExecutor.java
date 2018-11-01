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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateBackEvaluateExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private EvaluateBusiService evaluateBusiServiceImpl;

    @Autowired
    private EvaluateInfoService evaluateInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        EvaluateModel evaluateModel=(EvaluateModel)model;
        Map<String,Object> parameter=new HashMap<>();

        EvaluatePojo evaluatePojo=new EvaluatePojo();
        evaluatePojo.setEvaluateId(evaluateModel.getEvaluateId());
        evaluatePojo=evaluateInfoServiceImpl.selectOne(evaluatePojo);
        if(CommonUtil.isEmpty(evaluatePojo)){
            throw new BusinessException("传入的评价ID有误");
        }
        parameter.put("evaluateId",evaluateModel.getEvaluateId());
        parameter.put("isShow",evaluateModel.getIsShow());
        int i=evaluateBusiServiceImpl.updateEvaluate(parameter);
        if(i!=1){
            throw new BusinessException("更新评价状态失败");
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
        if(CommonUtil.isEmpty(evaluateModel.getIsShow())){
            error.rejectNull("isShow",null,"是否显示");
        }
        return super.validate(request, model, params);
    }

}
