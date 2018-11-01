package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CustomerBankrollInfoMapper extends InfoMapper<BusiCustomerBankrollPojo> {

    BusiCustomerBankrollPojo selectByPrimaryKey(Integer id);

    BusiCustomerBankrollPojo selectByCustomerId(Integer customerId);

    List<Map> findAllCustomerBankroll(BigDecimal amount);

    Map getfanliYoka(Integer customerId);
}