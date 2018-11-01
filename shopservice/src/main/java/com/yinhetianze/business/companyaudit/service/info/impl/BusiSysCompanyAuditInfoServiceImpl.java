package com.yinhetianze.business.companyaudit.service.info.impl;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysCompanyAuditPojo;
import com.yinhetianze.business.companyaudit.mapper.info.BusiSysCompanyAuditInfoMapper;
import com.yinhetianze.business.companyaudit.service.info.BusiSysCompanyAuditInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusiSysCompanyAuditInfoServiceImpl implements BusiSysCompanyAuditInfoService
{
    @Autowired
    private BusiSysCompanyAuditInfoMapper busiSysCompanyAuditInfoMapper;

    @Override
    public BusiSysCompanyAuditPojo selectNewestPojo(BusiSysCompanyAuditPojo busiSysCompanyAuditPojo) {

        List<BusiSysCompanyAuditPojo > busiSysCompanyAuditPojoList = busiSysCompanyAuditInfoMapper.select(busiSysCompanyAuditPojo);
        if(CommonUtil.isNotEmpty(busiSysCompanyAuditPojoList))
            return busiSysCompanyAuditPojoList.get(0);
        return null;
    }
}