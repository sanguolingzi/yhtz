package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductImgModel;
import com.yinhetianze.business.product.service.ProductImgInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.product.ProductImgPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询轮播图
 */

@Component
public class GetSlideshowImageExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private ProductImgInfoService productImgInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductImgModel productImgModel = (ProductImgModel)model;
        ProductImgPojo ProductImgPojo = new ProductImgPojo();
        BeanUtils.copyProperties(productImgModel,ProductImgPojo);
        ProductImgPojo.setDelFlag((short) 0);
        List<ProductImgPojo> productImgPojoList = productImgInfoServiceImpl.selectProductImgList(ProductImgPojo);
        return productImgPojoList;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductImgModel productImgModel = (ProductImgModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(productImgModel.getProductId() == null){
            errorMessage.rejectNull("productId",null,"商品id");
            return errorMessage;
        }
        return errorMessage;
    }
}
