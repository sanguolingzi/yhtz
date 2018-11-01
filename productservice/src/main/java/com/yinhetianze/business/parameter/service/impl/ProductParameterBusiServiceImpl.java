package com.yinhetianze.business.parameter.service.impl;

import com.yinhetianze.business.parameter.service.ProductParameterBusiService;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.parameter.mapper.ProductParameterBusiMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductParameterBusiServiceImpl implements ProductParameterBusiService
{
    @Autowired
    private ProductParameterBusiMapper mapper;

    @Override
    public int addProductParameter(ProductParameterPojo pojo)
    {
        return mapper.insert(pojo);
    }

    @Override
    public int modifyProductParameter(ProductParameterPojo pojo)
    {
        return mapper.updateByPrimaryKey(pojo);
    }

    @Override
    public int deleteProductParameter(ProductParameterPojo pojo)
    {
        // 删除商品参数与分类的关系
        int result = mapper.deleteProductParameterRelaByParamId(pojo.getId());
        if (result > 0)
        {
            LoggerUtil.info(ProductParameterBusiServiceImpl.class, "删除商品参数与分类关系成功：共[{}]条关系", new Object[]{result});
        }

        return mapper.deleteByPrimaryKey(pojo);
    }

    @Override
    public int bindProductCateParamRela(List<Map<String, Object>> relaList, Integer cateId)
    {
        // 删除原先的绑定关系
        int result = mapper.deleteProductParameterRelaByCateId(cateId);
        if (result > 0)
        {
            LoggerUtil.info(ProductParameterBusiServiceImpl.class, "删除商品分类[{}]与参数关系共:[{}]条", new Object[]{cateId, result});
        }

        // 绑定新的关系
        return mapper.bindProductCateParamRela(relaList);
    }
}