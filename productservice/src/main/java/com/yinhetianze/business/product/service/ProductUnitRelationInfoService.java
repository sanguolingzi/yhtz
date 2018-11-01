package com.yinhetianze.business.product.service;

import com.yinhetianze.business.product.model.ProductUnitModel;
import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;

import java.util.List;
import java.util.Map;

public interface ProductUnitRelationInfoService
{

    List<Map> getProductUnitRelationList(ProductUnitModel productUnitModel);

    int getProductUnitid(ProductUnitRelationPojo productUnitRelationPojo);

    List<Map> getProductUnitRelation(ProductUnitRelationModel productUnitRelationModel);

    List<Map> getCategoryUnit(ProductUnitRelationModel productUnitRelationModel);

    Integer getProductUnitName(Map<String,Object> map);

}