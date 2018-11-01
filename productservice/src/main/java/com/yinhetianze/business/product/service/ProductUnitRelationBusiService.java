package com.yinhetianze.business.product.service;

import com.yinhetianze.business.product.model.ProductUnitModel;
import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;

import java.util.List;
import java.util.Map;

public interface ProductUnitRelationBusiService
{

    int insertSelective(ProductUnitRelationPojo productUnitRelationPojo);

    int updateByPrimaryKeySelective(ProductUnitRelationPojo productUnitRelationPojo);

    int deleteBatch(String ids)throws BusinessException;

}