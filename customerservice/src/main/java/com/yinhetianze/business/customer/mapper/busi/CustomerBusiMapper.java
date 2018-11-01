package com.yinhetianze.business.customer.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;

public interface CustomerBusiMapper extends BusiMapper<BusiCustomerPojo> {
    int updatePartner(BusiCustomerPojo busiCustomerPojo);

    int setPartner(BusiCustomerPojo busiCustomerPojo);

    int cancelPartner(BusiCustomerPojo busiCustomerPojo);

    int cancelMember(BusiCustomerPojo busiCustomerPojo);

    int updateRecommendReward(BusiCustomerPojo busiCustomerPojo);

    int updateGameId(BusiCustomerPojo busiCustomerPojo);
}