package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.utils.ProductUtil;
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
 * 添加商品基本信息
 */
@Service
public class ModifyProductDetailsExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Autowired
    private ProductUtil productUtil;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductModel prodModel = (ProductModel) model;
        ProductPojo prodPojo = null;

        // 商品ID校验
        if (CommonUtil.isEmpty(prodModel.getProductId()))
        {
            throw new BusinessException("商品ID不能为空");
        }
        else
        {
            prodPojo = new ProductPojo();
            prodPojo.setId(prodModel.getProductId());
            //prodPojo.setpStatus((short)1); // 下架商品才能进行修改
            prodPojo = productInfoServiceImpl.findProduct(prodPojo);
            if (CommonUtil.isEmpty(prodPojo))
            {
                throw new BusinessException("可进行修改的商品信息不存在");
            }
        }

        // 设置商品固定非空信息
        productUtil.setProdFixInfo(prodPojo, prodModel);

        // 设置商品分类信息
        productUtil.setProdCate(prodPojo, prodModel);

        // 店铺信息
//        productUtil.setShopInfo(prodPojo, prodModel);

        // 设置商品品牌信息
        productUtil.setProdBrand(prodPojo, prodModel);

        // 设置商品单位
        productUtil.setProdUnit(prodPojo, prodModel);

        // 运费信息
        productUtil.setProdFreight(prodPojo, prodModel);

        // 标签信息
        productUtil.setProdTag(prodPojo, prodModel);

        // 显示价格与库存
        productUtil.setProdPriceStorage(prodPojo, prodModel);

        // 发货地信息
        productUtil.setShipAddress(prodPojo, prodModel);

        // 设置操作更新信息
        productUtil.setOperUpdateInfo(prodPojo, prodModel);

        try
        {
            int result = productBusiServiceImpl.modifyProduct(prodPojo);
            if (result <= 0)
            {
                throw new BusinessException("修改商品信息未成功");
            }
            else
            {
                LoggerUtil.info(ModifyProductDetailsExecutor.class, "修改商品信息成功。商品ID：{}", new Object[]{prodModel.getProductId()});
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(ModifyProductDetailsExecutor.class, e);
            throw new BusinessException("修改商品信息失败");
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
        return super.validate(request, model, params);
    }
}
