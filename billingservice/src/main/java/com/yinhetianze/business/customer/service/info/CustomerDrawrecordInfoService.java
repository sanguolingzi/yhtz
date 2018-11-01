package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;

import java.util.List;
import java.util.Map;

public interface CustomerDrawrecordInfoService
{
    List<BusiCustomerDrawrecordModel> selectList(BusiCustomerDrawrecordModel busiCustomerDrawrecordModel);

    List<BusiCustomerDrawrecordModel> selectListByCustomer(BusiCustomerDrawrecordModel busiCustomerDrawrecordModel);

    BusiCustomerDrawrecordPojo selectOne(Integer id);

    /**
     * 查询指定账户 在minuteInterval间隔内 的提现记录
     * @param paraMap
     * @return
     */
    int selectCount(Map<String,Object> paraMap);
}