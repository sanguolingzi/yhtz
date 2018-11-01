package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.business.shop.model.ShopProxyModel;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetBackstageShopProxyListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopProxyModel shopProxyModel=(ShopProxyModel)model;
        ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
        if(CommonUtil.isNotEmpty(shopProxyModel.getAccountName())){
            try {
                String accountName= URLDecoder.decode(shopProxyModel.getAccountName(),"UTF-8");
                shopProxyModel.setAccountName(accountName);
            }catch (Exception e){
                LoggerUtil.error(GetBackstageShopProxyListExecutor.class, e);
            }
        }
        if(CommonUtil.isNotEmpty(shopProxyModel.getShopName())){
            try {
                String shopName= URLDecoder.decode(shopProxyModel.getShopName(),"UTF-8");
                shopProxyModel.setShopName(shopName);
            }catch (Exception e){
                LoggerUtil.error(GetBackstageShopProxyListExecutor.class, e);
            }
        }
        BeanUtils.copyProperties(shopProxyModel,shopProxyPojo);
        PageHelper.startPage(shopProxyModel.getCurrentPage(),shopProxyModel.getPageSize());
        PageInfo pageInfo=new PageInfo(shopProxyInfoServiceImpl.selectShopProxyList(shopProxyPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
