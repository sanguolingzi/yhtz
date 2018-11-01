package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.business.shopbrand.service.info.ShopBrandInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 企业 查询店铺品牌列表
 */

@Component
public class GetShopBrandListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopBrandInfoService shopBrandInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopBrandModel busiShopBrandModel  = (BusiShopBrandModel)model;

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiShopBrandModel.getToken());
        BusiShopPojo BusiShop = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(BusiShop == null){
            throw new BusinessException("BC0001","店铺");
        }

        if(busiShopBrandModel.getIsAll() == null){
            PageHelper.startPage(busiShopBrandModel.getCurrentPage(),busiShopBrandModel.getPageSize());
        }

        busiShopBrandModel.setShopId(BusiShop.getId());
        List<BusiShopBrandModel> list = shopBrandInfoServiceImpl.selectList(busiShopBrandModel);
        PageInfo<BusiShopBrandModel> pageInfo = new PageInfo(list);
        PageData<BusiShopBrandModel> pageData= new PageData<BusiShopBrandModel>(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }


}
