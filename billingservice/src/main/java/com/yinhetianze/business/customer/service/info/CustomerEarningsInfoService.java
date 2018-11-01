package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.business.customer.model.BusiCustomerEaringModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CustomerEarningsInfoService
{
    Map<String,Object> selectDbCurrentTime();

    /**
     *
     * @param paraMap  customerId 收益拥有人  createIds 收益产生人Id集合
     * @return
     */
    List<BusiCustomerEaringModel> selectEaringList(Map<String, Object> paraMap);


    /**
     * 查找一级、二级、拓展市场收益
     * @param paraMap  customerId 收益拥有人 type 1 一级市场  2 二级市场 3拓展市场
     * @return
     */
    BigDecimal selectTotalEaring(Map<String, Object> paraMap);


    /**
     * 查询 指定用户产生的收益
     * @param paraMap  createId 收益产生人  customerId 收益拥有者  beginTime 开始时间 endTime 结束时间
     * @return
     */
    BigDecimal selectEaringByCondition(Map<String, Object> paraMap);
}