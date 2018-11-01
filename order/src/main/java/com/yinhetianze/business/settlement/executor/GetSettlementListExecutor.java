package com.yinhetianze.business.settlement.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.business.settlement.service.SettlementInfoService;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.pojo.order.SettlementPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GetSettlementListExecutor  extends AbstractRestBusiExecutor{

    @Autowired
    private SettlementInfoService settlementInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SettlementModel settlementModel=(SettlementModel)model;
        SettlementPojo settlementPojo=new SettlementPojo();
        BeanUtils.copyProperties(settlementModel,settlementPojo);
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(settlementModel.getToken());
        ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
        shopProxyPojo.setOptorId(tokenUser.getId());
        shopProxyPojo.setDelFlag((short)0);
        shopProxyPojo.setIsValid((short)0);
        shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
        settlementPojo.setShopId(shopProxyPojo.getId());
        PageHelper.startPage(settlementModel.getCurrentPage(),settlementModel.getPageSize());
        PageInfo pageInfo=new PageInfo(settlementInfoServiceImpl.selectSettlementsList(settlementPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
