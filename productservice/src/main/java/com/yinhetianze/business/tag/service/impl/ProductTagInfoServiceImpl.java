package com.yinhetianze.business.tag.service.impl;

import com.yinhetianze.business.tag.service.ProductTagInfoService;
import com.yinhetianze.pojo.tag.ProductTagPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.tag.mapper.ProductTagInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductTagInfoServiceImpl implements ProductTagInfoService
{
    @Autowired
    private ProductTagInfoMapper mapper;

    @Override
    public List<ProductTagPojo> getProductTagList(Map<String, Object> params)
    {
        return mapper.getProductTagList(params);
    }

    @Override
    public List<ProductTagPojo> getProductTagByName(String tagName)
    {
        return mapper.getProductTagByName(tagName);
    }

    @Override
    public ProductTagPojo getProductTagById(ProductTagPojo pojo)
    {
        return mapper.selectOne(pojo);
    }
}