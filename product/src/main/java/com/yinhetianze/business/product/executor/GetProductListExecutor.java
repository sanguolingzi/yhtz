package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductModel;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询商品基本信息
 */
@Service
public class GetProductListExecutor extends AbstractRestBusiExecutor<PageData<ProductPojo>>
{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected PageData<ProductPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductModel prodModel = (ProductModel) model;
        Map<String, Object> prodParam = new HashMap<>();

        if (CommonUtil.isNotEmpty(prodModel.getProdName()))
        {
            try {
                prodParam.put("prodName", URLDecoder.decode(prodModel.getProdName(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (CommonUtil.isNotEmpty(prodModel.getProdCode()))
        {
            prodParam.put("prodCode", prodModel.getProdCode());
        }

        if (CommonUtil.isNotEmpty(prodModel.getProductId()))
        {
            prodParam.put("prodId", prodModel.getProductId());
        }

        if (CommonUtil.isNotEmpty(prodModel.getDelFlag()))
        {
            prodParam.put("delFlag", prodModel.getDelFlag());
        }

        if (CommonUtil.isNotEmpty(prodModel.getIsReplacement()))
        {
            prodParam.put("isReplacement", prodModel.getIsReplacement());
        }

        if (CommonUtil.isNotEmpty(prodModel.getShopId()))
        {
            prodParam.put("shopId", prodModel.getShopId());
        }

        if (CommonUtil.isNotEmpty(prodModel.getAuditState()))
        {
            prodParam.put("auditState", prodModel.getAuditState());
        }

        if (CommonUtil.isNotEmpty(prodModel.getpStatus()))
        {
            prodParam.put("prodStatus", prodModel.getpStatus());
        }

        if (CommonUtil.isNotEmpty(prodModel.getIsFreightFree()))
        {
            prodParam.put("isFreightFree", prodModel.getIsFreightFree());
        }

        if (CommonUtil.isNotEmpty(prodModel.getProdBrandId()))
        {
            prodParam.put("prodBrandId", prodModel.getProdBrandId());
        }

        if (CommonUtil.isNotEmpty(prodModel.getProdCateId()))
        {
            prodParam.put("prodCateId", prodModel.getProdCateId());
        }

        if (CommonUtil.isNotEmpty(prodModel.getShipAddress()))
        {
            prodParam.put("shipAddress", prodModel.getShipAddress());
        }

        if (CommonUtil.isNotEmpty(prodModel.getLowSellPrice()))
        {
            prodParam.put("lowSellPrice", prodModel.getLowSellPrice());
        }

        if (CommonUtil.isNotEmpty(prodModel.getHighSellPrice()))
        {
            prodParam.put("highSellPrice", prodModel.getHighSellPrice());
        }
        if (CommonUtil.isNotEmpty(prodModel.getProductType()))
        {
            prodParam.put("productType", prodModel.getProductType());
        }
        //结算类型
        if (CommonUtil.isNotEmpty(prodModel.getSettlement()))
        {
            prodParam.put("settlement", prodModel.getSettlement());
        }
        //商品类型
        if (CommonUtil.isNotEmpty(prodModel.getProductDistinction()))
        {
            prodParam.put("productDistinction", prodModel.getProductDistinction());
        }
        List<ProductPojo> prodList = null;
        try
        {
            PageHelper.startPage(prodModel.getCurrentPage(), prodModel.getPageSize());

            prodList = productInfoServiceImpl.getProductList(prodParam);

            PageInfo<ProductPojo> pageInfo = new PageInfo<>(prodList);

            return new PageData<ProductPojo>(pageInfo.getList(), pageInfo.getTotal());
        }
        catch (Exception e)
        {
            LoggerUtil.error(GetProductListExecutor.class, e);
            throw new BusinessException("根据条件查询产品信息失败");
        }
    }
}
