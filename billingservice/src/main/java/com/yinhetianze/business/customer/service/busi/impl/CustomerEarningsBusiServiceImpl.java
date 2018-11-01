package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerEarningsBusiMapper;
import com.yinhetianze.business.customer.service.busi.CustomerEarningsBusiService;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.CustomerEarningsPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerEarningsBusiServiceImpl implements CustomerEarningsBusiService
{
    @Autowired
    private CustomerEarningsBusiMapper mapper;

    @Override
    public int addInfo(CustomerEarningsPojo customerEarningsPojo) {
        return mapper.insertSelective(customerEarningsPojo);
    }


    @Override
    public int addInfoBatch(List<CustomerEarningsPojo> list) {
        try{
            return mapper.addInfoBatch(list);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                // donothing
            }else{
                LoggerUtil.error(CustomerEarningsBusiServiceImpl.class,"addInfoBatch failed:"+e.getMessage());
            }
        }
        return 0;
    }
}