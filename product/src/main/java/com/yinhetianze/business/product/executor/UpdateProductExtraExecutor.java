package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductExtraBusiService;
import com.yinhetianze.business.product.service.ProductExtraInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductExtraPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据商品ID更新商品拓展信息
 * 没有拓展信息记录则新增，有则执行修改
 */
@Service
public class UpdateProductExtraExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductExtraBusiService productExtraBusiServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductExtraInfoService productExtraInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductModel prodModel = (ProductModel) model;

        if (CommonUtil.isNotEmpty(prodModel.getProductId()))
        {
            // 获取当前商品对应的基本信息
            ProductPojo prodPojo = new ProductPojo();
            prodPojo.setId(prodModel.getProductId());
            prodPojo = productInfoServiceImpl.findProduct(prodPojo);
            if (CommonUtil.isEmpty(prodPojo))
            {
                throw new BusinessException("没有当前商品信息");
            }

            // 获取商品拓展信息，然后新添加/修改商品拓展信息
            ProductExtraPojo extraPojo = new ProductExtraPojo();
            extraPojo.setProdId(prodModel.getProductId());
            // 根据prodId查询的extra信息
            ProductExtraPojo originalPojo = productExtraInfoServiceImpl.findProductExtra(extraPojo);

            // 预先设置执行更新/新增操作的数据。
            if (CommonUtil.isNotEmpty(prodModel.getProdIntroduction()))
            {
                // 商品介绍详情
                extraPojo.setProdIntroduction(prodModel.getProdIntroduction());
            }
            if (CommonUtil.isNotEmpty(prodModel.getParamList()))
            {
                // 商品参数列表转json字符串
                extraPojo.setProdParam(CommonUtil.objectToJsonString(prodModel.getParamList()));
            }

            try
            {
                int result = 0;
                // 如果没有originalPojo就执行新增操作，有则执行修改操作
                if (CommonUtil.isEmpty(originalPojo))
                {
                    // 执行新增操作
                    result = productExtraBusiServiceImpl.addProductExtra(extraPojo);
                    if (result > 0)
                    {
                        LoggerUtil.info(UpdateProductExtraExecutor.class, "添加商品拓展信息成功。商品ID：{}", new Object[]{prodModel.getProductId()});
                    }
                }
                else
                {
                    // 执行修改操作
                    result = productExtraBusiServiceImpl.modifyProductExtra(extraPojo);
                    if (result > 0)
                    {
                        LoggerUtil.info(UpdateProductExtraExecutor.class, "修改商品拓展信息成功。商品ID：{}", new Object[]{prodModel.getProductId()});
                    }
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(UpdateProductExtraExecutor.class, e);
                throw new BusinessException("更新商品拓展信息失败");
            }
        }
        else
        {
            throw new BusinessException("商品ID不能为空");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductModel prodModel = (ProductModel) model;

        // 商品ID不能为空
        if (CommonUtil.isEmpty(prodModel.getProductId()))
        {
            errors.rejectNull("productId", null,"商品Id");
        }

        return errors;
    }
}
