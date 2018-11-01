package com.yinhetianze.business.parameter.service;

import com.yinhetianze.pojo.product.ProductParameterPojo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ProductParameterBusiService
{
    int addProductParameter(ProductParameterPojo pojo);

    int modifyProductParameter(ProductParameterPojo pojo);

    @Transactional
    int deleteProductParameter(ProductParameterPojo pojo);

    /**
     * 删除先前的绑定关系，然后重新添加新选择的关系
     * @param relaList
     * @return
     */
    @Transactional
    int bindProductCateParamRela(List<Map<String, Object>> relaList, Integer cateId);
}