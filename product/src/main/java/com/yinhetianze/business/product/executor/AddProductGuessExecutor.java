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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 增加猜你喜欢的商品
 */
@Service
public class AddProductGuessExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private ProductGuessInfoService productGuessInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductGuessPojo productGuessPojo=new ProductGuessPojo();
        BeanUtils.copyProperties(model,productGuessPojo);
        int result=productGuessInfoServiceImpl.insertSelective(productGuessPojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";

    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductGuessModel productGuessModel=(ProductGuessModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(productGuessModel.getProdId())){
            errorMessage.rejectNull("prodId",null,"关联数据Id");
            return errorMessage;
        }
        return errorMessage;
    }
}
