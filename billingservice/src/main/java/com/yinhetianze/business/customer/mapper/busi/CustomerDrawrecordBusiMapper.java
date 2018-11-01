package com.yinhetianze.business.customer.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;

public interface CustomerDrawrecordBusiMapper extends BusiMapper<BusiCustomerDrawrecordPojo> {
    /**
     * 后台提交审核记录 防止重复提交 限制修改语句执行条件中 auditStatus = 2
     * @param busiCustomerDrawrecordPojo
     * @return
     */
    int updateForAudit(BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo);
}