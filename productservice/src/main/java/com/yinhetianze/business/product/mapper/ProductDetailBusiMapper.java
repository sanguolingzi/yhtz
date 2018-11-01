package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.ProductDetailPojo;

import java.util.List;
import java.util.Map;

public interface ProductDetailBusiMapper extends BusiMapper<ProductDetailPojo> {

    int addProductDetail(List<ProductDetailPojo> pojoList);

    int modifyProductDetailByProdId(ProductDetailPojo productDetailPojo);

    int updatePDStorage(Map<String,Object> map);

	int updateProductDetailAuditing(ProductDetailPojo productDetailPojo);

 	int addShopProductDetail(List<ProductDetailPojo> detailPojoList);}