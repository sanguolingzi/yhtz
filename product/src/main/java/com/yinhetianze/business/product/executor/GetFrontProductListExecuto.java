package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductPageModel;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Service
public class GetFrontProductListExecuto extends AbstractRestBusiExecutor {
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected PageData<ProductPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductPageModel prodModel = (ProductPageModel) model;
        if(CommonUtil.isNotEmpty(prodModel.getProdName())){
            try {
                String prodName = URLDecoder.decode(prodModel.getProdName(), "UTF-8");
                prodModel.setProdName(prodName);
            }catch (Exception e){
                LoggerUtil.error(GetFrontProductListExecuto.class, e);
            }
        }
        if(prodModel.getIsAll() == null){
            PageHelper.startPage(prodModel.getCurrentPage(), prodModel.getPageSize());
        }
        PageInfo pageInfo=new PageInfo(productInfoServiceImpl.getMoBileProductList(prodModel));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return  pageData;
    }
}
