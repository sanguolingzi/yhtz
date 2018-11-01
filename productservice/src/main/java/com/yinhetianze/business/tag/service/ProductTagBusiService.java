package com.yinhetianze.business.tag.service;

import com.yinhetianze.pojo.tag.ProductTagPojo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface ProductTagBusiService
{
    int addProductTag(ProductTagPojo pojo);

    int modifyProductTag(ProductTagPojo pojo);

    /**
     * 删除商品标签，级联删除所有当前标签挂载的商品关系
     * @param tagId
     * @return
     */
    @Transactional
    int deleteProductTag(Integer tagId);

    int bindProductTag(Map<String, Object> params);
}