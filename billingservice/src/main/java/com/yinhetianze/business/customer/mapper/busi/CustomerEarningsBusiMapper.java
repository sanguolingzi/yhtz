package com.yinhetianze.business.customer.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.customer.CustomerEarningsPojo;

import java.util.List;

public interface CustomerEarningsBusiMapper extends BusiMapper<CustomerEarningsPojo> {
    int addInfoBatch(List<CustomerEarningsPojo> list);
}