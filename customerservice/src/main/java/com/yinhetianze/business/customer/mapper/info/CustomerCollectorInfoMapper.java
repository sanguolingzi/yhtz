package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.model.BusiCustomerProductCollectorModel;
import com.yinhetianze.business.customer.model.BusiCustomerShopCollectorModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerCollectorPojo;
import java.util.List;
import java.util.Map;

public interface CustomerCollectorInfoMapper extends InfoMapper<BusiCustomerCollectorPojo> {

    List<BusiCustomerCollectorModel> selectList(BusiCustomerCollectorModel busiCustomerCollectorModel);

    List<BusiCustomerProductCollectorModel> selectProductCollect(BusiCustomerCollectorModel busiCustomerCollectorModel);

    List<BusiCustomerShopCollectorModel> selectShopCollect(BusiCustomerCollectorModel busiCustomerCollectorModel);

    Map selectCount(BusiCustomerCollectorModel busiCustomerCollectorModel);
}