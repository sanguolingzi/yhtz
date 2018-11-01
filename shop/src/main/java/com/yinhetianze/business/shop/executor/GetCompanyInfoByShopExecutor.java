package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 企业 查询基础信息
 */

@Component
public class GetCompanyInfoByShopExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopCompanyPojo busiShopCompanyPojo = new BusiShopCompanyPojo();
        BusiShopCompanyModel busiShopCompanyModel = (BusiShopCompanyModel)model;
        busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectOne(busiShopCompanyPojo);
        return busiShopCompanyPojo;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiShopCompanyModel busiShopCompanyModel = (BusiShopCompanyModel)model;

        return errorMessage;
    }
}
