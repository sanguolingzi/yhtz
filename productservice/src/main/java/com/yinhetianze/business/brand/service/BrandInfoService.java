package com.yinhetianze.business.brand.service;

import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.pojo.brand.BrandPojo;

import java.util.List;
import java.util.Map;

public interface BrandInfoService
{
    /**
     * 根据条件查询品牌列表
     * @param param
     * @return
     */
    List<BrandPojo> getBrandList(Map<String, Object> param);

    /**
     * 根据ID，附加其他条件查询单个品牌信息
     * @param pojo
     * @return
     */
    BrandPojo findBrand(BrandPojo pojo);

    List<Map> selectBrandList(ProductModel productModel);
}