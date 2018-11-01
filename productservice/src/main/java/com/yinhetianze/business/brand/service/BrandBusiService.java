package com.yinhetianze.business.brand.service;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.brand.BrandPojo;

/**
 * 商品品牌Service
 * 增，删，改等事务操作
 */
public interface BrandBusiService
{
    /**
     * 添加品牌
     * @param pojo
     * @return
     */
    int addBrand(BrandPojo pojo);

    /**
     * 根据ID修改品牌
     * @param pojo
     * @return
     */
    int modifyBrand(BrandPojo pojo);

    /**
     * 根据ID删除品牌
     * @param
     * @return
     */
    int deleteBrand(BrandPojo brandPojo);

}