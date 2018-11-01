package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.service.ProductUnitRelationInfoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetProductStateExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ProductInfoService productInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductModel productModel=(ProductModel)model;
        //根据店铺Id查询该店铺下的不同状态商品 状态前台页面根据需要的状态进行传递
        Map productMap =new HashMap();
        productMap.put("shopId",productModel.getShopId());
        productMap.put("auditState",productModel.getAuditState());
        productMap.put("prodStatus",productModel.getpStatus());
        PageHelper.startPage(productModel.getCurrentPage(),productModel.getPageSize());
        PageInfo pageInfo = new PageInfo(productInfoServiceImpl.getProductList(productMap));
        return pageInfo;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductModel productModel = (ProductModel) model;
        if (CommonUtil.isEmpty(productModel.getShopId()))
        {
            errors.rejectNull("shopId", null, "店铺Id");
        }

        return errors;
    }
}
