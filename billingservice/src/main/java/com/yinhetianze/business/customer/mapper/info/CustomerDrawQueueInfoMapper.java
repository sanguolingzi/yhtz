package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerDrawQueuePojo;

import java.util.List;
import java.util.Map;

public interface CustomerDrawQueueInfoMapper extends InfoMapper<BusiCustomerDrawQueuePojo> {

    List<BusiCustomerDrawQueuePojo> selectList(Map paraMap);
}