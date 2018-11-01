package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class GetBackstageShopExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopModel busiShopModel=(BusiShopModel)model;
        BusiShopPojo busiShopPojo=new BusiShopPojo();
        BeanUtils.copyProperties(busiShopModel,busiShopPojo);
        busiShopPojo=shopInfoServiceImpl.selectOne(busiShopPojo);
        return busiShopPojo;
    }
}
