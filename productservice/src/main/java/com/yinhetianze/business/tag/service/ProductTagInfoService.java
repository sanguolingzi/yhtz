package com.yinhetianze.business.tag.service;

import com.yinhetianze.pojo.tag.ProductTagPojo;

import java.util.List;
import java.util.Map;

public interface ProductTagInfoService
{
    List<ProductTagPojo> getProductTagList(Map<String, Object> params);

    List<ProductTagPojo> getProductTagByName(String tagName);

    ProductTagPojo getProductTagById(ProductTagPojo pojo);
}