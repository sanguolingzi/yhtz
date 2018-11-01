package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;


public interface CustomerDrawrecordBusiService
{
    int AddCustomerDrawrecordInfo(BusiCustomerBankrollPojo busiCustomerBankrollPojo,
                                     BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) throws BusinessException;

    int AddCustomerDrawrecordInfoForWechat(BusiCustomerBankrollPojo busiCustomerBankrollPojo,
                                  BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) throws BusinessException;

    /**
     * 微信提现成功后 处理 账户余额 修改提现状态
     * @return
     */
    int updateCustomerDrawrecordInfoForWechat(BusiCustomerBankrollPojo busiCustomerBankrollPojo,
                                              BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo)  throws BusinessException;

    /**
     * 微信提现失败后 处理 账户余额 修改提现状态
     * @return
     */
    int updateCustomerDrawrecordInfoForWechat(BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo)  throws BusinessException;

    /**
     * 后台 操作修改用户提现记录状态
     * @param busiCustomerDrawrecordModel
     * @return
     */
    int updateCustomerDrawrecordInfo(BusiCustomerDrawrecordModel busiCustomerDrawrecordModel)  throws BusinessException;

    /**
     * 微信支付 失败 修改的参数
     * @param busiCustomerDrawrecordPojo
     * @return
     */
    int updateInfo(BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo);


    int updateByPrimaryKeySelective(BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo);


}