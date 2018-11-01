package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GetSelfSupportShopExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopPojo busiShopPojo=new BusiShopPojo();
        int shopId = Integer.parseInt(sysPropertiesUtils.getStringValue("shopId"));
        busiShopPojo.setId(shopId);
        PageInfo pageInfo=new PageInfo(shopInfoServiceImpl.selectSelfSupportShop(busiShopPojo));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return  pageData;
    }
}
