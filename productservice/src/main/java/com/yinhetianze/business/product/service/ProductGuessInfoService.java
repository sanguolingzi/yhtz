package com.yinhetianze.business.product.service;

import com.yinhetianze.business.product.model.ProductGuessModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.product.ProductGuessPojo;
import com.yinhetianze.pojo.product.ProductPojo;

import java.util.List;
import java.util.Map;

public interface ProductGuessInfoService
{
    int insertSelective(ProductGuessPojo productGuessPojo);

    List<Map> getProductGuessList(ProductGuessModel ProductGuessModel);

    int deleteBatch(ProductGuessModel ProductGuessModel)throws BusinessException;

    int updateByPrimaryKeySelective(ProductGuessPojo ProductGuessPojo);

    int getProductGuess(ProductGuessModel productGuessModel);
}