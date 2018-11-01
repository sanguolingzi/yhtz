package com.yinhetianze.business.tag.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.tag.ProductTagPojo;

import java.util.Map;

public interface ProductTagBusiMapper extends BusiMapper<ProductTagPojo> {

    int bindProductTag(Map<String, Object> param);

    int deleteProductTagRela(Integer tagId);

    int deleteProductTag(Integer tagId);
}