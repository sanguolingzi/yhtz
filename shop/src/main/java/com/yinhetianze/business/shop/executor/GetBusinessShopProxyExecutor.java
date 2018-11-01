package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.shop.model.ShopProxyModel;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetBusinessShopProxyExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopProxyModel shopProxyModel=(ShopProxyModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
        shopProxyPojo.setShopAccount(tokenUser.getUsername());
        shopProxyPojo.setDelFlag((short)0);
        shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
        List<ShopProxyPojo>list=new ArrayList<ShopProxyPojo>();
        list.add(shopProxyPojo);
        PageHelper.startPage(shopProxyModel.getCurrentPage(),shopProxyModel.getPageSize());
        PageInfo pageInfo=new PageInfo(list);
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
