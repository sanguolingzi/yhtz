package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetCompanyinfoAddressExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> responseMap=new HashMap<String,Object>();
        BusiShopCompanyModel busiShopCompanyModel=(BusiShopCompanyModel)model;
        try {
            BusiShopCompanyPojo busiShopCompanyPojo=new BusiShopCompanyPojo();
            busiShopCompanyPojo.setId(busiShopCompanyModel.getId());
            busiShopCompanyPojo.setAuditStatus((short)0);
            busiShopCompanyPojo=shopCompanyInfoServiceImpl.selectOne(busiShopCompanyPojo);
            String [] addressArrar={busiShopCompanyPojo.getRegionLocation(),busiShopCompanyPojo.getCity(),busiShopCompanyPojo.getAreaCounty()};
            responseMap.put("addressArrar",addressArrar);
        }catch (Exception e){
            LoggerUtil.error(GetCompanyinfoAddressExecutor.class, e);
            throw new BusinessException("获取企业地址失败");
        }
        return responseMap;
    }
}
