package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;

public interface CustomerRecommendRelationBusiService
{
    int insertSelective(BusiCustomerRecommendRelationPojo record);

    /**
     * 绑定推荐人关系
     * @param record
     * @return
     */
    int bindRelation(BusiCustomerRecommendRelationPojo record) throws BusinessException;


    int updateByPrimaryKeySelective(BusiCustomerRecommendRelationPojo record);
}