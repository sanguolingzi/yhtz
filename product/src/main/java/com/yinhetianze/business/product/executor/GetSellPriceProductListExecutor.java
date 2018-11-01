package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GetSellPriceProductListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductModel productModel=(ProductModel)model;
        PageHelper.startPage(productModel.getCurrentPage(),productModel.getPageSize());
        PageInfo pageInfo=new PageInfo(productInfoServiceImpl.selectSellPriceProductList());
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
