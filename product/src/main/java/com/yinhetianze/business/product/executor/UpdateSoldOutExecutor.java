package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductSkuModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateSoldOutExecutor extends AbstractRestBusiExecutor<String>{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ProductModel productModel = (ProductModel) model;
        for(int productId :productModel.getProductArr()){
            ProductPojo ProductPojo=new ProductPojo();
            ProductPojo.setpStatus((short) 1);
            ProductPojo.setId(productId);
            int i= productBusiServiceImpl.modifyProduct(ProductPojo);
            if (i != 1) {
                throw new BusinessException("商品下架失败");
            }
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductModel prodModel = (ProductModel) model;

        // 商品ID数组不能为空
        if (CommonUtil.isEmpty(prodModel.getProductArr())) {
            errors.rejectNull("productId", null, "商品Id");
        }
        for(int productId :prodModel.getProductArr()){
            //判断该商品是否存在
            ProductPojo upProductPojo =new ProductPojo();
            //定时上架 商品必须是待上架状态 而且是审核通过的 时间小于等于当前时间的商品可以上架
            upProductPojo.setpStatus((short) 0);
            upProductPojo.setAuditState((short)3);
            upProductPojo.setId(productId);
            ProductPojo product=productInfoServiceImpl.findProduct(upProductPojo);
            if(CommonUtil.isEmpty(product)){
                errors.rejectError("productId", null, "已审核上架商品才能下架");
                return errors;
            }
        }
        return errors;
    }

}
