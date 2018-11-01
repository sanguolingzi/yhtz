package com.yinhetianze.business.parameter.executor;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.service.ProductParameterBusiService;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class DeleteProductParameterExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductParameterInfoService productParameterInfoServiceImpl;

    @Autowired
    private ProductParameterBusiService productParameterBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductParameterModel paramModel = (ProductParameterModel) model;

        if (CommonUtil.isNotEmpty(paramModel.getParamId()))
        {
            ProductParameterPojo paramPojo = new ProductParameterPojo();
            paramPojo.setId(paramModel.getParamId());
            paramPojo = productParameterInfoServiceImpl.findProductParameter(paramPojo);
            if (CommonUtil.isNotEmpty(paramPojo))
            {
                try
                {
                    int result = productParameterBusiServiceImpl.deleteProductParameter(paramPojo);
                    if (result > 0)
                    {
                        LoggerUtil.info(DeleteProductParameterExecutor.class, "删除商品参数成功：{}", new Object[]{paramPojo.getId()});
                    }
                    else
                    {
                        throw new BusinessException("删除商品参数未成功");
                    }
                }
                catch (Exception e)
                {
                    LoggerUtil.error(DeleteProductParameterExecutor.class, e);
                    throw new BusinessException("删除商品参数失败");
                }
            }
            else
            {
                throw new BusinessException("商品参数不存在");
            }
        }
        else
        {
            throw new BusinessException("商品参数ID不能为空");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductParameterModel paramModel = (ProductParameterModel) model;

        // 参数ID不能为空
        if (CommonUtil.isEmpty(paramModel.getParamId()))
        {
            errors.rejectNull("paramId", null,"参数Id");
        }

        return errors;
    }
}
