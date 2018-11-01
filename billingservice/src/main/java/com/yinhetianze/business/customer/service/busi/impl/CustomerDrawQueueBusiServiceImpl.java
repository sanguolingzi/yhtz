package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerDrawQueueBusiMapper;
import com.yinhetianze.business.customer.service.busi.CustomerDrawQueueBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerDrawrecordBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerDrawQueuePojo;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerDrawQueueBusiServiceImpl implements CustomerDrawQueueBusiService
{
    @Autowired
    private CustomerDrawQueueBusiMapper mapper;

    @Autowired
    private CustomerDrawrecordBusiService customerDrawrecordBusiServiceImpl;

    @Override
    public int addInfo(BusiCustomerDrawQueuePojo busiCustomerDrawQueuePojo) {
        return mapper.insertSelective(busiCustomerDrawQueuePojo);
    }


    @Override
    public int updateForPaySuccess(Integer queueId, Integer drawRecordId) {
        //更改 提现记录状态 为支付成功
        BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo = new BusiCustomerDrawrecordPojo();
        busiCustomerDrawrecordPojo.setId(drawRecordId);

        busiCustomerDrawrecordPojo.setAuditStatus((short)0);
        customerDrawrecordBusiServiceImpl.updateByPrimaryKeySelective(busiCustomerDrawrecordPojo);

        BusiCustomerDrawQueuePojo queuePojo = new BusiCustomerDrawQueuePojo();
        queuePojo.setId(queueId);
        queuePojo.setIsHandle((short)1);
        updateByPrimaryKeySelective(queuePojo);
        return 1;
    }

    @Override
    public int updateForPayFailed(BusiCustomerDrawQueuePojo busiCustomerDrawQueuePojo, BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) throws BusinessException {
        busiCustomerDrawQueuePojo.setIsHandle((short)1);
        updateByPrimaryKeySelective(busiCustomerDrawQueuePojo);
        customerDrawrecordBusiServiceImpl.updateCustomerDrawrecordInfoForWechat(busiCustomerDrawrecordPojo);
        return 1;
    }

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerDrawQueuePojo busiCustomerDrawQueuePojo) {
        busiCustomerDrawQueuePojo.setHandleTime(new Date());
        return mapper.updateByPrimaryKeySelective(busiCustomerDrawQueuePojo);
    }
}