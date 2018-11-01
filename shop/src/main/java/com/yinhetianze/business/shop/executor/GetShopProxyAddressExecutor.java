package com.yinhetianze.business.shop.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.business.shop.model.ShopProxyModel;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetShopProxyAddressExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> responseMap=new HashMap<String,Object>();
        ShopProxyModel shopProxyModel=(ShopProxyModel)model;
        try {
            ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
            shopProxyPojo.setId(shopProxyModel.getId());
            shopProxyPojo.setDelFlag((short)0);
            shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
            String [] addressArrar={shopProxyPojo.getShopProvince(),shopProxyPojo.getShopCity(),shopProxyPojo.getShopArea()};
            responseMap.put("addressArrar",addressArrar);
        }catch (Exception e){
            LoggerUtil.error(GetShopProxyAddressExecutor.class, e);
            throw new BusinessException("获取店铺地址失败");
        }
        return responseMap;
    }
}
