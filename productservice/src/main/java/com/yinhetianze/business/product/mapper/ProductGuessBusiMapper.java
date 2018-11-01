package com.yinhetianze.business.product.mapper;


import com.yinhetianze.business.product.model.ProductGuessModel;
import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.ProductGuessPojo;

import java.util.List;
import java.util.Map;

public interface ProductGuessBusiMapper extends BusiMapper<ProductGuessPojo> {

    List<Map> getProductGuessList(ProductGuessModel productGuessModel);

    int getProductGuess(ProductGuessModel productGuessModel);
}