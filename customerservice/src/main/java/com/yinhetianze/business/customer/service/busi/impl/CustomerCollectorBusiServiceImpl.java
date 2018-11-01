package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerCollectorBusiMapper;
import com.yinhetianze.business.customer.service.busi.CustomerCollectorBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerCollectorPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CustomerCollectorBusiServiceImpl implements CustomerCollectorBusiService
{
    @Autowired
    private CustomerCollectorBusiMapper customerCollectorBusiMapper;

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerCollectorPojo record) {
        return customerCollectorBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int addInfo(BusiCustomerCollectorPojo record)  throws BusinessException {
        try{
            return customerCollectorBusiMapper.insertSelective(record);
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                DuplicateKeyException t = (DuplicateKeyException)e;
                throw new BusinessException("您已收藏该商品!");
            }
            LoggerUtil.error(CustomerCollectorBusiServiceImpl.class,e.getMessage());
            return 0;
        }
    }

    @Override
    public int deleteInfo(BusiCustomerCollectorPojo record) throws BusinessException {
        return customerCollectorBusiMapper.deleteInfo(record);
    }
}