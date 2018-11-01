package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.model.BusiCustomerProductCollectorModel;
import com.yinhetianze.business.customer.model.BusiCustomerShopCollectorModel;
import com.yinhetianze.pojo.customer.BusiCustomerCollectorPojo;

import java.util.List;

public interface CustomerCollectorInfoService
{
    List<BusiCustomerCollectorModel> selectList(BusiCustomerCollectorModel busiCustomerCollectorModel);

    List<BusiCustomerProductCollectorModel> selectCollectorProductList(BusiCustomerCollectorModel busiCustomerCollectorModel);

    List<BusiCustomerShopCollectorModel> selectCollectorShopList(BusiCustomerCollectorModel busiCustomerCollectorModel);

    Integer selectCount(BusiCustomerCollectorModel busiCustomerCollectorModel);

    BusiCustomerCollectorPojo selectOne(BusiCustomerCollectorPojo busiCustomerCollectorPojo);
}