package com.yinhetianze.business.activity.executor;

import com.yinhetianze.business.activity.model.ActivityProductModel;
import com.yinhetianze.business.activity.service.busi.ActivityProductBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ActivityProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteActivityProductExecutor extends AbstractRestBusiExecutor {


    @Autowired
    private ActivityProductBusiService activityProductBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ActivityProductModel activityProductModel=(ActivityProductModel)model;
        ActivityProductPojo activityProductPojo=new ActivityProductPojo();
        activityProductPojo.setId(activityProductModel.getId());
        activityProductPojo.setDelFlag((short)1);
        int result=activityProductBusiServiceImpl.updateByPrimaryKeySelective(activityProductPojo);
        if(result<=0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ActivityProductModel activityProductModel=(ActivityProductModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(activityProductModel.getId())){
            errorMessage.rejectNull("id","null","商品ID");
        }
        return errorMessage;
    }
}
