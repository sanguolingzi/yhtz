package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.business.customer.model.BusiAmountFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerBankrollFlowInfoMapper extends InfoMapper<BusiCustomerBankrollFlowPojo> {
    List<BusiCustomerBankrollFlowModel> selectList(BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel);

    List<Map<String,Object>> selectConsumption(@Param("paraMap") Map<String, Object> paraMap);

    List<BusiAmountFlowModel> selectPersonal(@Param("paraMap") Map<String,Object> paraMap);

    List<BusiCustomerBankrollFlowModel> selectMarket(@Param("paraMap") Map<String,Object> paraMap);
}