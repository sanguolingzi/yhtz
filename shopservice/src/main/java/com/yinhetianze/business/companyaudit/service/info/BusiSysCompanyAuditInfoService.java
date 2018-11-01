package com.yinhetianze.business.companyaudit.service.info;

import com.yinhetianze.pojo.back.BusiSysCompanyAuditPojo;

public interface BusiSysCompanyAuditInfoService
{
    /**
     * 查找最新的一条审核记录
     * @param busiSysCompanyAuditPojo
     * @return
     */
    BusiSysCompanyAuditPojo selectNewestPojo(BusiSysCompanyAuditPojo busiSysCompanyAuditPojo);
}