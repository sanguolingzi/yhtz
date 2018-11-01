package com.yinhetianze.business.parameter.executor;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class GetProductParameterExecutor extends AbstractRestBusiExecutor<ProductParameterPojo>
{
    @Autowired
    private ProductParameterInfoService productParameterInfoServiceImpl;

    @Override
    protected ProductParameterPojo executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductParameterModel paramModel = (ProductParameterModel) model;

        if (CommonUtil.isNotEmpty(paramModel.getParamId()))
        {
            ProductParameterPojo paramPojo = new ProductParameterPojo();
            paramPojo.setId(paramModel.getParamId());
            paramPojo = productParameterInfoServiceImpl.findProductParameter(paramPojo);
            if (CommonUtil.isEmpty(paramPojo))
            {
                throw new BusinessException("商品参数不存在");
            }

            return paramPojo;
        }
        else
        {
            throw new BusinessException("商品参数ID为空");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductParameterModel paramModel = (ProductParameterModel) model;

        if (CommonUtil.isEmpty(paramModel.getParamId()))
        {
            errors.rejectNull("paramId", null,"参数Id");
        }

        return errors;
    }
}
