package com.yinhetianze.business.companyaudit.service.busi.impl;

import com.yinhetianze.business.shop.service.busi.ShopCompanyBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.BusiSysCompanyAuditPojo;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.business.companyaudit.mapper.busi.BusiSysCompanyAuditMapper;
import com.yinhetianze.business.companyaudit.model.BusiSysCompanyAuditModel;
import com.yinhetianze.business.companyaudit.service.busi.BusiSysCompanyAuditService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class BusiSysCompanyAuditServiceImpl implements BusiSysCompanyAuditService
{
    @Autowired
    private BusiSysCompanyAuditMapper busiSysCompanyAuditMapper;

    @Autowired
    private ShopCompanyBusiService shopCompanyBusiServiceImpl;

    @Override
    public int addInfo(BusiSysCompanyAuditModel busiSysCompanyAuditModel){
        BusiSysCompanyAuditPojo busiSysCompanyAuditPojo = new BusiSysCompanyAuditPojo();
        BeanUtils.copyProperties(busiSysCompanyAuditModel,busiSysCompanyAuditPojo);
        busiSysCompanyAuditMapper.insertSelective(busiSysCompanyAuditPojo);

        /*
        BusiShopCompanyPojo busiShopCompanyPojo = new BusiShopCompanyPojo();
        busiShopCompanyPojo.setId(busiSysCompanyAuditPojo.getCompanyId());
        busiShopCompanyPojo.setAuditStatus(busiSysCompanyAuditPojo.getAuditStatus());
        int result = shopCompanyBusiServiceImpl.updateByPrimaryKeySelective(busiShopCompanyPojo);
        if(result <= 0)
            throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        */
        return 1;
    }

    @Override
    public int updateInfo(BusiSysCompanyAuditModel busiSysCompanyAuditModel) {







        return 0;
    }
}