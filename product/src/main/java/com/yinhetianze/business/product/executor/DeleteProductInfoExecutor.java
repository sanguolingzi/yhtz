package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductInfoModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 逻辑删除商品规格
 */
@Service
public class DeleteProductInfoExecutor extends AbstractRestBusiExecutor<String>{
    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private ProductDetailBusiService productDetailBusiServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ProductInfoModel productInfoModel = (ProductInfoModel) model;
        // 获取商品信息，判断是否存在
        ProductPojo prodPojo = new ProductPojo();
        prodPojo.setId(productInfoModel.getProductId());
        prodPojo = productInfoServiceImpl.findProduct(prodPojo);
        if (CommonUtil.isEmpty(prodPojo)) {
            throw new BusinessException("商品信息不存在");
        } else if (prodPojo.getpStatus() == 0) {// 上架状态不能删除
            throw new BusinessException("商品处于上架状态不能删除");
        } else if (prodPojo.getDelFlag() == 1) {        // 已经删除则不用执行
            return null;
        }
        //商品规格skuCode查询
        ProductDetailPojo productDetailPojoParam=new ProductDetailPojo();
        productDetailPojoParam.setSkuCode(productInfoModel.getSkuCode());
        //根据skuCode查询修改规格存不存在
        ProductDetailPojo  productDetailPojo = productDetailInfoServiceImpl.selectProductDetailPojo(productDetailPojoParam);
        if (CommonUtil.isEmpty(productDetailPojo)){
                throw new BusinessException("删除规格不存在");
            }

        try {
            ProductDetailPojo paramProductDetailPojo =new ProductDetailPojo();
            paramProductDetailPojo.setId(productDetailPojo.getId());
            paramProductDetailPojo.setDelFlag((short)1);
            // 执行商品规格(逻辑删除)
            int result = productDetailBusiServiceImpl.deleteProductSpec(paramProductDetailPojo);
            if (result <= 0) {
                throw new BusinessException("删除规格未成功");
            } else {
                LoggerUtil.info(DeleteProductInfoExecutor.class, "删除商品[{}]成功", new Object[]{productInfoModel.getProductId()});
            }
        } catch (Exception e) {
            LoggerUtil.error(DeleteProductInfoExecutor.class, e);
            throw new BusinessException("删除规格信息失败");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductInfoModel productInfoModel = (ProductInfoModel) model;

        if (CommonUtil.isEmpty(productInfoModel.getProductId())) {
            errors.rejectNull("productId", null,"商品Id");
        }
        if(CommonUtil.isEmpty(productInfoModel.getSkuCode())){
            errors.rejectNull("skuCode", null,"商品编码");
        }
        return errors;
    }
}
