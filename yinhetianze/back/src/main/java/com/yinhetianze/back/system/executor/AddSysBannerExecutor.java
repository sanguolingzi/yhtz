package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.systemservice.system.service.busi.SysBannerBusiService;
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
 * 新增轮播图
 */
@Component
public class AddSysBannerExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysBannerBusiService sysBannerBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = sysBannerBusiServiceImpl.addInfo((SysBannerModel)model);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysBannerModel sysBannerModel = (SysBannerModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(sysBannerModel.getSort() == null){
            errorMessage.rejectNull("sort",null,"排序号");
            return errorMessage;
        }
        if(sysBannerModel.getIsShow() == null){
           errorMessage.rejectNull("isShow",null,"是否显示");
           return errorMessage;
        }
        if(sysBannerModel.getPhotoUrl() == null){
           errorMessage.rejectNull("photoUrl",null,"图片地址");
           return errorMessage;
        }

        return errorMessage;
    }
}
