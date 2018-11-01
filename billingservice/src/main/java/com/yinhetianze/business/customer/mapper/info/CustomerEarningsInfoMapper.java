package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.business.customer.model.BusiCustomerEaringModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.CustomerEarningsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerEarningsInfoMapper extends InfoMapper<CustomerEarningsPojo> {

    Map<String, Object> selectDbCurrentTime();

    List<BusiCustomerEaringModel> selectEaringList(@Param("paraMap") Map<String, Object> paraMap);

    Map<String,Object> selectTotalEaring(@Param("paraMap") Map<String, Object> paraMap);

    Map<String,Object> selectEaringByCondition(@Param("paraMap") Map<String, Object> paraMap);
}