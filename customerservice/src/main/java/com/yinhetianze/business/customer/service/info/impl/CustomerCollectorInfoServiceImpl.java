package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerCollectorInfoMapper;
import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.model.BusiCustomerProductCollectorModel;
import com.yinhetianze.business.customer.model.BusiCustomerShopCollectorModel;
import com.yinhetianze.business.customer.service.info.CustomerCollectorInfoService;
import com.yinhetianze.pojo.customer.BusiCustomerCollectorPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomerCollectorInfoServiceImpl implements CustomerCollectorInfoService
{
    @Autowired
    private CustomerCollectorInfoMapper customerCollectorInfoMapper;

    @Override
    public List<BusiCustomerCollectorModel> selectList(BusiCustomerCollectorModel busiCustomerCollectorModel) {
        return customerCollectorInfoMapper.selectList(busiCustomerCollectorModel);
    }

    @Override
    public BusiCustomerCollectorPojo selectOne(BusiCustomerCollectorPojo busiCustomerCollectorPojo) {
        busiCustomerCollectorPojo.setDelFlag((short)0);
        return customerCollectorInfoMapper.selectOne(busiCustomerCollectorPojo);
    }

    @Override
    public Integer selectCount(BusiCustomerCollectorModel busiCustomerCollectorModel) {
        Map<String,Object> m = customerCollectorInfoMapper.selectCount(busiCustomerCollectorModel);
        Object temp = m.getOrDefault("count","0");
        return Integer.parseInt(temp.toString());
    }


    @Override
    public List<BusiCustomerProductCollectorModel> selectCollectorProductList(BusiCustomerCollectorModel busiCustomerCollectorModel) {
        return customerCollectorInfoMapper.selectProductCollect(busiCustomerCollectorModel);
    }

    @Override
    public List<BusiCustomerShopCollectorModel> selectCollectorShopList(BusiCustomerCollectorModel busiCustomerCollectorModel) {
        return customerCollectorInfoMapper.selectShopCollect(busiCustomerCollectorModel);
    }
}