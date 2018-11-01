package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.category.CategoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询商品种类
 */
@Service
public class getProductUnitcategoryExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Override
    protected Map<String, Object> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> responseMap=new HashMap<String,Object>();
        ProductUnitRelationModel productUnitRelationModel=(ProductUnitRelationModel)model;
        try {
            CategoryPojo categoryPojo=new CategoryPojo();
            categoryPojo.setId(productUnitRelationModel.getCategoryid());
            CategoryPojo categoryPojoParent= categoryInfoServiceImpl.findCategory(categoryPojo);
            categoryPojo.setId(categoryPojoParent.getParentId());
            CategoryPojo categoryPojoLevelParent= categoryInfoServiceImpl.findCategory(categoryPojo);
            int[] categoryArrar = {categoryPojoLevelParent.getParentId(),categoryPojoParent.getParentId(),productUnitRelationModel.getCategoryid()};
            responseMap.put("categoryArrar",categoryArrar);
        }catch (Exception e){
            LoggerUtil.error(GetProductDetailsExecutor.class, e);
            throw new BusinessException("获取商品详细信息失败");
        }

        return responseMap;
    }
}
