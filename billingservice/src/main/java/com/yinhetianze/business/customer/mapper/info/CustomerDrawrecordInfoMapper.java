package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;

import java.util.List;
import java.util.Map;

public interface CustomerDrawrecordInfoMapper extends InfoMapper<BusiCustomerDrawrecordPojo> {

    List<BusiCustomerDrawrecordModel> selectList(BusiCustomerDrawrecordModel busiCustomerDrawrecordModel);

    List<BusiCustomerDrawrecordModel> selectListByCustomer(BusiCustomerDrawrecordModel busiCustomerDrawrecordModel);

    int selectCount(Map<String,Object> paraMap);
}