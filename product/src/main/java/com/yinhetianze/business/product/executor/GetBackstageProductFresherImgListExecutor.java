package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductFresherImgModel;
import com.yinhetianze.business.product.service.ProductFresherImgInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.product.ProductFresherImgPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class GetBackstageProductFresherImgListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductFresherImgInfoService productFresherImgInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductFresherImgModel productFresherImgModel=(ProductFresherImgModel)model;
        ProductFresherImgPojo productFresherImgPojo=new ProductFresherImgPojo();
        BeanUtils.copyProperties(productFresherImgModel,productFresherImgPojo);
        productFresherImgPojo.setDelFlag((short)0);
        List<ProductFresherImgPojo> productFresherImgPojoList=productFresherImgInfoServiceImpl.selectProductFresherImgList(productFresherImgPojo);
        return productFresherImgPojoList;
    }
}
