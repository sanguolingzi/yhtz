package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 逻辑删除商品信息
 */
@Service
public class DeleteProductExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductModel prodModel = (ProductModel) model;

        if (CommonUtil.isEmpty(prodModel.getProductId()))
        {
            throw new BusinessException("商品ID不能为空");
        }

        // 获取商品信息，判断是否存在
        ProductPojo prodPojo = new ProductPojo();
        prodPojo.setId(prodModel.getProductId());
        prodPojo = productInfoServiceImpl.findProduct(prodPojo);
        if (CommonUtil.isEmpty(prodPojo))
        {
            throw new BusinessException("商品信息不存在");
        }
        else if (prodPojo.getpStatus() == 0) // 上架状态不能删除
        {
            throw new BusinessException("商品处于上架状态不能删除");
        }
        else if (prodPojo.getDelFlag() == 1) // 已经删除则不用执行
        {
            return null;
        }

        try
        {
            // 执行删除商品信息
            int result = productBusiServiceImpl.deleteProduct(prodPojo);
            if (result <= 0)
            {
                throw new BusinessException("删除商品未成功");
            }
            else
            {
                LoggerUtil.info(DeleteProductExecutor.class, "删除商品[{}]成功", new Object[]{prodModel.getProductId()});
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(DeleteProductExecutor.class, e);
            throw new BusinessException("删除商品信息失败");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductModel prodModel = (ProductModel) model;

        if (CommonUtil.isEmpty(prodModel.getProductId()))
        {
            errors.rejectNull("productId", null,"商品Id");
        }

        return errors;
    }
}
