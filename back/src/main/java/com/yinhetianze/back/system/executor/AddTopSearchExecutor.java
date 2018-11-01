package com.yinhetianze.back.system.executor;


import com.yinhetianze.systemservice.system.model.TopSearchModel;
import com.yinhetianze.systemservice.system.service.busi.TopSearchBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.TopSearchPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增轮播图
 */
@Transactional
@Component
public class AddTopSearchExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private TopSearchBusiService topSearchBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
       TopSearchPojo topSearchPojo = new TopSearchPojo();
        BeanUtils.copyProperties(model,topSearchPojo);
        int result = topSearchBusiServiceImpl.insertSelective(topSearchPojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        TopSearchModel topSearchModel = (TopSearchModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(topSearchModel.getSort() == null){
            errorMessage.rejectError("sort",null,"排序号");
        }

        if(topSearchModel.getHotWord() == null){
            errorMessage.rejectNull("hotWord",null,"热词");
            return errorMessage;
        }

        return errorMessage;
    }
}
