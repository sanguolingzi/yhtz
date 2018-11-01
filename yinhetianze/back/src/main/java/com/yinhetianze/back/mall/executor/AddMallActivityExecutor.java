package com.yinhetianze.back.mall.executor;

import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.systemservice.mall.service.busi.MallActivityBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增商城活动
 */
@Component
public class AddMallActivityExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private MallActivityBusiService mallActivityBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = mallActivityBusiServiceImpl.addInfo((MallActivityModel)model);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        MallActivityModel mallActivityModel = (MallActivityModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(mallActivityModel.getActivityname() == null){
            errorMessage.rejectError("activityname",null,"活动名称");
        }

        if(mallActivityModel.getActivityimage() == null){
            errorMessage.rejectNull("activityimage",null,"活动图片");
            return errorMessage;
        }

       if(mallActivityModel.getActivitylink() == null){
           errorMessage.rejectNull("activitylink",null,"活动链接");
           return errorMessage;
       }
        return errorMessage;
    }
}
