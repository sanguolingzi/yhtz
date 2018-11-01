package com.yinhetianze.business.brand.service.impl;

import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.brand.service.BrandBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.brand.BrandPojo;
import com.yinhetianze.pojo.product.ProductUnitPojo;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.brand.mapper.BrandBusiMapper;

@Service
public class BrandBusiServiceImpl implements BrandBusiService
{
    @Autowired
    private BrandBusiMapper mapper;

    @Override
    public int addBrand(BrandPojo pojo)
    {
        return mapper.insertSelective(pojo);
    }

    @Override
    public int modifyBrand(BrandPojo pojo)
    {
        return mapper.updateByPrimaryKey(pojo);
    }

    @Override
    public int deleteBrand(BrandPojo brandPojo){
        return  mapper.updateByPrimaryKeySelective(brandPojo);
    }

}