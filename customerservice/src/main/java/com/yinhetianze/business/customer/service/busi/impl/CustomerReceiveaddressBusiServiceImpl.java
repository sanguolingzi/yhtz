package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerReceiveaddressBusiMapper;
import com.yinhetianze.business.customer.service.busi.CustomerReceiveaddressBusiService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CustomerReceiveaddressBusiServiceImpl implements CustomerReceiveaddressBusiService
{
    @Autowired
    private CustomerReceiveaddressBusiMapper customerReceiveaddressBusiMapper;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Override
    public int insert(BusiCustomerReceiveaddressPojo record) {
        return customerReceiveaddressBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiCustomerReceiveaddressPojo record) {
        return customerReceiveaddressBusiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerReceiveaddressPojo record){
        return customerReceiveaddressBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiCustomerReceiveaddressPojo record) {
        return customerReceiveaddressBusiMapper.updateByPrimaryKey(record);
    }


    @Override
    public int addCustomerAddress(BusiCustomerReceiveaddressPojo record) throws BusinessException {

        List<BusiCustomerReceiveaddressPojo> busiCustomerReceiveaddressPojoList = customerReceiveaddressInfoServiceImpl.selectByCustomerId(
                record.getCustomerId());
        //首次添加地址 默认设置为收获地址
        if(CommonUtil.isEmpty(busiCustomerReceiveaddressPojoList)){
            record.setDefaultStatus((short)0);
            insertSelective(record);
        }else{
            insertSelective(record);
            if(0 == record.getDefaultStatus()){
                updateDefaultAddress(record);
            }
        }
        return 1;
    }

    @Override
    public int updateCustomerAddress(BusiCustomerReceiveaddressPojo record) throws BusinessException {

        if(0 == record.getDefaultStatus()){
            updateDefaultAddress(record);
        }
        return updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateDefaultAddress(BusiCustomerReceiveaddressPojo record) throws BusinessException {
        //清空该customerid 下 默认收获地址标记
        List<BusiCustomerReceiveaddressPojo> busiCustomerReceiveaddressPojoList = customerReceiveaddressInfoServiceImpl.selectByCustomerId(
                record.getCustomerId());
        BusiCustomerReceiveaddressPojo temp = null;
        if(CommonUtil.isNotEmpty(busiCustomerReceiveaddressPojoList)){
            for(BusiCustomerReceiveaddressPojo pojo :busiCustomerReceiveaddressPojoList){
                if(pojo.getDefaultStatus() == 0){
                    temp = new BusiCustomerReceiveaddressPojo();
                    temp.setId(pojo.getId());
                    temp.setDefaultStatus((short)1);
                    updateByPrimaryKeySelective(temp);
                }
            }
        }

        //重新设置 这里只处理 默认收货地址这个字段
        temp = new BusiCustomerReceiveaddressPojo();
        temp.setId(record.getId());
        temp.setDefaultStatus((short)0);
        temp.setCustomerId(record.getCustomerId());
        int result = updateByPrimaryKeySelective(temp);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return 1;
    }
}