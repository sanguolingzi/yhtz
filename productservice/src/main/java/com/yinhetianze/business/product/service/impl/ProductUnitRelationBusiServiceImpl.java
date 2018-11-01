package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.ProductUnitBusiMapper;
import com.yinhetianze.business.product.mapper.ProductUnitRelationBusiMapper;
import com.yinhetianze.business.product.mapper.ProductUnitRelationInfoMapper;
import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.business.product.service.ProductUnitRelationBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.product.ProductUnitPojo;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductUnitRelationBusiServiceImpl implements ProductUnitRelationBusiService
{
    @Autowired
    private ProductUnitRelationBusiMapper mapper;

    @Autowired
    private ProductUnitRelationInfoMapper productUnitRelationInfoMapper;

    @Autowired
    private ProductUnitBusiMapper productUnitBusiMapper;


    @Override
    public int insertSelective(ProductUnitRelationPojo productUnitRelationPojo) {
        return mapper.insertSelective(productUnitRelationPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ProductUnitRelationPojo productUnitRelationPojo) {
        return mapper.updateByPrimaryKeySelective(productUnitRelationPojo);
    }

    @Override
    public int deleteBatch(String ids) throws BusinessException {
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for (String id:idsArr){
            ProductUnitRelationPojo productUnitRelationPojo=new ProductUnitRelationPojo();
            productUnitRelationPojo.setId(Integer.parseInt(id));
            productUnitRelationPojo.setDelFlag((short)1);
            ProductUnitPojo productUnitPojo=new ProductUnitPojo();
            int unitid=productUnitRelationInfoMapper.getProductUnitid(productUnitRelationPojo);
            if(unitid<= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            productUnitPojo.setId(unitid);
            productUnitPojo.setDelFlag((short)1);
            int results=this.productUnitBusiMapper.updateByPrimaryKeySelective(productUnitPojo);
            if(results <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            int result=this.updateByPrimaryKeySelective(productUnitRelationPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }
}