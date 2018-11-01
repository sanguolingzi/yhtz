package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.system.model.QuestionModel;
import com.yinhetianze.systemservice.system.service.info.QuestionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
@Service
public class GetQuestionExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private QuestionInfoService questionInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        QuestionModel questionModel=(QuestionModel)model;
        if(CommonUtil.isNotEmpty(questionModel.getQuestionName())){
            try {
                String questionName= URLDecoder.decode(questionModel.getQuestionName(),"UTF-8");
                questionModel.setQuestionName(questionName);
            }catch (Exception e){
                LoggerUtil.error(GetSysDictionaryExecutor.class, e);
            }
        }
        List<QuestionModel> list=questionInfoServiceImpl.getQuestion(questionModel);
        PageInfo pageInfo=new PageInfo(list);
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
