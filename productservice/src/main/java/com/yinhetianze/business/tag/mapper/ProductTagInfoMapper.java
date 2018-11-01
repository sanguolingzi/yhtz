package com.yinhetianze.business.tag.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.tag.ProductTagPojo;

import java.util.List;
import java.util.Map;

public interface ProductTagInfoMapper extends InfoMapper<ProductTagPojo> {

    List<ProductTagPojo> getProductTagList(Map<String, Object> params);

    List<ProductTagPojo> getProductTagByName(String tagName);

}