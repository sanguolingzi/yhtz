package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerWechatBusiMapper;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerWechatBusiService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerWechatBusiServiceImpl implements CustomerWechatBusiService
{
    @Autowired
    private CustomerWechatBusiMapper mapper;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;


    @Override
    public int insertSelective(BusiCustomerWechatPojo busiCustomerWechatPojo) {
        return mapper.insertSelective(busiCustomerWechatPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerWechatPojo busiCustomerWechatPojo) {
        busiCustomerWechatPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(busiCustomerWechatPojo);
    }

    @Override
    public int wxRegeister(BusiRegeisterModel busiRegeisterModel) throws BusinessException {

        BusiCustomerWechatPojo busiCustomerWechatPojo = customerWechatInfoServiceImpl.selectByCustomerCode(busiRegeisterModel.getCustomerCode());
        if(busiCustomerWechatPojo == null)
            return 0;

        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setPhone(busiRegeisterModel.getPhone());
        //TODO 设置默认密码 666  这里可能要修改
        //String loginPassword = busiRegeisterModel.getLoginPassword();
        //if(CommonUtil.isEmpty(loginPassword))
        //    loginPassword= "666666";

        //busiCustomerPojo.setLoginPassword(loginPassword);
        customerBusiServiceImpl.addCustomerInfo(busiCustomerPojo);

        busiCustomerWechatPojo.setCustomerId(busiCustomerPojo.getId());
        busiCustomerWechatPojo.setIsRegeister((short)1);
        return updateByPrimaryKeySelective(busiCustomerWechatPojo);
    }

    @Override
    public int cancleBind(BusiCustomerWechatPojo busiCustomerWechatPojo) {
        return mapper.cancleBind(busiCustomerWechatPojo);
    }
}