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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetPcProductListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductPageModel productPageModel = (ProductPageModel) model;
        if (CommonUtil.isNotEmpty(productPageModel.getProdName())) {
            try {
                /*
                try {
                    LoggerUtil.error(GetPcProductListExecutor.class, "前端传入的prodName值=================" + productPageModel.getProdName());
                    LoggerUtil.error(GetPcProductListExecutor.class, "UTF-8:" + new String(productPageModel.getProdName().getBytes(), "UTF-8"));
                    LoggerUtil.error(GetPcProductListExecutor.class, "GB2312:" + new String(productPageModel.getProdName().getBytes(), "GB2312"));
                    LoggerUtil.error(GetPcProductListExecutor.class, "GBK:" + new String(productPageModel.getProdName().getBytes(), "GBK"));
                } catch (Exception e) {
                    LoggerUtil.error(GetPcProductListExecutor.class, e.getMessage());
                }
                */
                String prodName = URLDecoder.decode(productPageModel.getProdName(), "UTF-8");
                LoggerUtil.info(GetPcProductListExecutor.class, prodName);
                productPageModel.setProdName(prodName);
                PageHelper.startPage(productPageModel.getCurrentPage(), productPageModel.getPageSize());
                PageInfo pageInfo = new PageInfo(productInfoServiceImpl.getPcProductList(productPageModel));
                PageData pageData = new PageData(pageInfo.getList(), pageInfo.getTotal());
                return pageData;
            } catch (Exception e) {
                LoggerUtil.error(GetPcProductListExecutor.class, e);
                throw new BusinessException("查询商品信息失败");
            }
        } else {
            PageHelper.startPage(productPageModel.getCurrentPage(), productPageModel.getPageSize());
            PageInfo pageInfo = new PageInfo(productInfoServiceImpl.getPcProductList(productPageModel));
            PageData pageData = new PageData(pageInfo.getList(), pageInfo.getTotal());
            return pageData;
        }
    }
}
