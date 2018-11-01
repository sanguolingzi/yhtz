package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.model.ProductGuessModel;
import com.yinhetianze.business.product.service.ProductGuessInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.product.ProductGuessPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductGuessBusiMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class ProductGuessInfoServiceImpl implements ProductGuessInfoService
{
    @Autowired
    private ProductGuessBusiMapper mapper;

    @Override
    public int insertSelective(ProductGuessPojo productGuessPojo) {
        return mapper.insertSelective(productGuessPojo);
    }


    @Override
    public List<Map> getProductGuessList(ProductGuessModel ProductGuessModel) {
        return mapper.getProductGuessList(ProductGuessModel);
    }

    @Override
    public int deleteBatch(ProductGuessModel productGuessModel) throws BusinessException {
        ProductGuessPojo productGuessPojo=new ProductGuessPojo();
        productGuessPojo.setId(productGuessModel.getId());
        productGuessPojo.setDelFlag((short)1);
        int result=updateByPrimaryKeySelective(productGuessPojo);
        if(result<=0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }

    @Override
    public int updateByPrimaryKeySelective(ProductGuessPojo productGuessPojo) {
        return mapper.updateByPrimaryKeySelective(productGuessPojo);
    }

    @Override
    public int getProductGuess(ProductGuessModel productGuessModel) {
        return mapper.getProductGuess(productGuessModel);
    }
}