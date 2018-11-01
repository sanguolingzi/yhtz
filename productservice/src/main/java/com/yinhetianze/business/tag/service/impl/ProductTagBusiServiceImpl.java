package com.yinhetianze.business.tag.service.impl;

import com.yinhetianze.business.tag.service.ProductTagBusiService;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.tag.ProductTagPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.tag.mapper.ProductTagBusiMapper;

import java.util.Map;

@Service
public class ProductTagBusiServiceImpl implements ProductTagBusiService
{
    @Autowired
    private ProductTagBusiMapper mapper;

    @Override
    public int addProductTag(ProductTagPojo pojo)
    {
        return mapper.insert(pojo);
    }

    @Override
    public int modifyProductTag(ProductTagPojo pojo)
    {
        return mapper.updateByPrimaryKey(pojo);
    }

    @Override
    public int deleteProductTag(Integer tagId)
    {
        int result = mapper.deleteProductTagRela(tagId);
        LoggerUtil.info(ProductTagBusiServiceImpl.class, "删除标签[{}]关联的商品记录[{}]", new Object[]{tagId, result});

        return mapper.deleteProductTag(tagId);
    }

    @Override
    public int bindProductTag(Map<String, Object> params)
    {
        return mapper.bindProductTag(params);
    }

}