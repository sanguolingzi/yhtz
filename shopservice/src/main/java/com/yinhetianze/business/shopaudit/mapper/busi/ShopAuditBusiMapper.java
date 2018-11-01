package com.yinhetianze.business.shopaudit.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;
import org.apache.ibatis.annotations.Param;

public interface ShopAuditBusiMapper extends BusiMapper<BusiSysShopAuditPojo> {
    /**
     * 修改 审核记录的 可编辑状态
     * @param customerId
     * @param relationId
     * @return
     */
    int updateDoUpdate(@Param("customerId") Integer customerId, @Param("relationId") Integer relationId);
}