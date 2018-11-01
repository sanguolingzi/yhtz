package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.pojo.customer.BusiCustomerDrawQueuePojo;

import java.util.List;
import java.util.Map;

public interface CustomerDrawQueueInfoService
{
    /**
     * 查询指定间隔的 待处理提现记录
     * @param paraMap    minuteInterval 间隔分钟数
     * @return
     */
    List<BusiCustomerDrawQueuePojo> selectList(Map paraMap);
}