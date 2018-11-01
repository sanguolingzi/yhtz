package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.business.customer.model.BusiCustomerOrderModel;
import com.yinhetianze.business.customer.model.BusiCustomerPageModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;

import java.util.List;
import java.util.Map;

public interface CustomerInfoMapper extends InfoMapper<BusiCustomerPojo> {

    List<BusiCustomerOrderModel> selectList(BusiCustomerPageModel busiCustomerPageModel);

    Map getOneCustomer(BusiCustomerPojo busiCustomerPojo);
}