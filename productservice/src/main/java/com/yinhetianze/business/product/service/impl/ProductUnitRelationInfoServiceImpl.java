package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.ProductUnitBusiMapper;
import com.yinhetianze.business.product.model.ProductUnitModel;
import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.business.product.service.ProductUnitRelationInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.product.ProductUnitPojo;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductUnitRelationInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductUnitRelationInfoServiceImpl implements ProductUnitRelationInfoService
{
    @Autowired
    private ProductUnitRelationInfoMapper mapper;

    @Override
    public List<Map> getProductUnitRelationList(ProductUnitModel productUnitModel) {

        return mapper.getProductUnitRelationList(productUnitModel);
    }

    @Override
    public int getProductUnitid(ProductUnitRelationPojo productUnitRelationPojo) {
        return mapper.getProductUnitid(productUnitRelationPojo);
    }

    @Override
    public List<Map> getProductUnitRelation(ProductUnitRelationModel productUnitRelationModel) {
        return mapper.getProductUnitRelation(productUnitRelationModel);
    }

    @Override
    public List<Map> getCategoryUnit(ProductUnitRelationModel productUnitRelationModel){
        return mapper.getCategoryUnit(productUnitRelationModel);
    }

    @Override
    public Integer getProductUnitName(Map<String,Object> map) {
        return mapper.getProductUnitName(map);
    }
}