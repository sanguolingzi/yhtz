package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductGuessModel;
import com.yinhetianze.business.product.service.ProductGuessInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductGuessPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UpdateProductGuessExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ProductGuessInfoService productGuessInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductGuessModel productGuessModel=(ProductGuessModel)model;
        ProductGuessPojo productGuessPojo=new ProductGuessPojo();
        BeanUtils.copyProperties(productGuessModel,productGuessPojo);
        if(productGuessInfoServiceImpl.updateByPrimaryKeySelective(productGuessPojo)<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductGuessModel productGuessModel=(ProductGuessModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(productGuessModel.getId())){
            errorMessage.rejectNull("id",null,"猜你喜欢id");
        }
        if(CommonUtil.isEmpty(productGuessModel.getSort())){
            errorMessage.rejectNull("sort",null,"排序号");
        }
        return errorMessage;
    }
}
