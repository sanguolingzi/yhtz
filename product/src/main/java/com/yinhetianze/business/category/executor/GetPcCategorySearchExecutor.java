package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.brand.service.BrandInfoService;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetPcCategorySearchExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private BrandInfoService brandInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        List<Map> categorySearch = new ArrayList<Map>();
        Map<String, Object> brand=new HashMap<>();
        List<Map> classifyList=brandInfoServiceImpl.selectBrandList((ProductModel)model);
        brand.put("titName","品牌名称");
        brand.put("classifyList",classifyList);
        categorySearch.add(brand);
        return categorySearch;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductModel productModel=(ProductModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(productModel.getProdCateId())){
            errorMessage.rejectError("prodCateId", "BC0006", "商品分类ID");
        }
        return errorMessage;
    }

}
