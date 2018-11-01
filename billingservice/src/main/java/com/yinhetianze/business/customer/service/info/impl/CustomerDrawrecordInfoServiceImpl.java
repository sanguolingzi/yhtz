package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerDrawrecordInfoMapper;
import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.business.customer.service.info.CustomerDrawrecordInfoService;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerDrawrecordInfoServiceImpl implements CustomerDrawrecordInfoService
{

    @Autowired
    private CustomerDrawrecordInfoMapper customerDrawrecordInfoMapper;

    @Override
    public List<BusiCustomerDrawrecordModel> selectList(BusiCustomerDrawrecordModel busiCustomerDrawrecordModel) {
        return customerDrawrecordInfoMapper.selectList(busiCustomerDrawrecordModel);
    }

    @Override
    public BusiCustomerDrawrecordPojo selectOne(Integer id) {
        BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo = new BusiCustomerDrawrecordPojo();
        busiCustomerDrawrecordPojo.setId(id);
        return customerDrawrecordInfoMapper.selectOne(busiCustomerDrawrecordPojo);
    }

    @Override
    public List<BusiCustomerDrawrecordModel> selectListByCustomer(BusiCustomerDrawrecordModel busiCustomerDrawrecordModel) {
        return customerDrawrecordInfoMapper.selectListByCustomer(busiCustomerDrawrecordModel);
    }

    @Override
    public int selectCount(Map<String,Object> paraMap) {
        return customerDrawrecordInfoMapper.selectCount(paraMap);
    }
}