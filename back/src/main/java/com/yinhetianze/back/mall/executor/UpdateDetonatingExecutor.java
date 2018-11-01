package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.mall.model.DetonatingModel;
import com.yinhetianze.systemservice.mall.service.busi.DetonatingBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UpdateDetonatingExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private DetonatingBusiService detonatingBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = detonatingBusiServiceImpl.updateInfo((DetonatingModel) model);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        DetonatingModel detonatingModel=(DetonatingModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(detonatingModel.getId())){
            errorMessage.rejectNull("id","null","展示ID");
        }
        if(CommonUtil.isEmpty(detonatingModel.getDetonatingName())){
            errorMessage.rejectNull("detonatingName","null","展示名称");
        }
        if(CommonUtil.isEmpty(detonatingModel.getPhotoUrl())){
            errorMessage.rejectNull("photoUrl","null","展示图片");
        }
        if(CommonUtil.isEmpty(detonatingModel.getProductId())){
            errorMessage.rejectNull("productId","null","关联的商品Id");
        }
        if(CommonUtil.isEmpty(detonatingModel.getIsShow())){
            errorMessage.rejectNull("isShow","null","是否在首页显示");
        }
        if(CommonUtil.isEmpty(detonatingModel.getSort())){
            errorMessage.rejectNull("sort","null","排序号");
        }
        return errorMessage;
    }
}
