package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductFresherModel;
import com.yinhetianze.business.product.service.ProductFresherBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductFresherPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteProductFresherExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductFresherBusiService productFresherBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductFresherModel productFresherModel=(ProductFresherModel)model;
        ProductFresherPojo productFresherPojo=new ProductFresherPojo();
        productFresherPojo.setId(productFresherModel.getId());
        productFresherPojo.setDelFlag((short)1);
        int result=productFresherBusiServiceImpl.updateByPrimaryKeySelective(productFresherPojo);
        if (result<=0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductFresherModel productFresherModel=(ProductFresherModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(productFresherModel.getId())){
            errorMessage.rejectNull("id",null, "id");
        }
        return errorMessage;
    }
}
