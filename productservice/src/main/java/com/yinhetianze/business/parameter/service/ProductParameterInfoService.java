package com.yinhetianze.business.parameter.service;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.pojo.product.ProductParameterPojo;

import java.util.List;
import java.util.Map;

public interface ProductParameterInfoService
{
    /**
     * 查询满足条件的参数实体
     * 全条件皆以等号判断
     * @param pojo
     * @return
     */
    List<ProductParameterPojo> getProductParameterList(ProductParameterPojo pojo);

    /**
     * 查询满足条件的参数实体
     * 名称条件以模糊匹配方式查询
     * @param pojo
     * @return
     */
    List<ProductParameterPojo> getProductParameterList(Map<String, Object> pojo);

    /**
     * 根据id获取匹配条件的实体类
     * @param pojo
     * @return
     */
    ProductParameterPojo findProductParameter(ProductParameterPojo pojo);

    /**
     * 根据分类Id查询参数
     * @param paramModel
     * @return
     */
    List<Map> getProductCategoryIdParameter(ProductParameterModel paramModel);
}