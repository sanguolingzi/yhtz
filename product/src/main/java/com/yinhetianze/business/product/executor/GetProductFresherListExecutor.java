package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductFresherModel;
import com.yinhetianze.business.product.service.ProductFresherInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductFresherPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetProductFresherListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductFresherInfoService productFresherInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductFresherModel productFresherModel=(ProductFresherModel)model;
        ProductFresherPojo productFresherPojo=new ProductFresherPojo();
        if(CommonUtil.isNotEmpty(productFresherModel.getProdName())){
            try {
                String prodName = URLDecoder.decode(productFresherModel.getProdName(), "UTF-8");
                productFresherPojo.setProdName(prodName);
            }catch (Exception e){
                LoggerUtil.error(GetProductFresherListExecutor.class, e);
            }
        }
        PageHelper.startPage(productFresherModel.getCurrentPage(), productFresherModel.getPageSize());
        PageInfo pageInfo=new PageInfo(productFresherInfoServiceImpl.selectProductFresherList(productFresherPojo));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return  pageData;
    }
}
