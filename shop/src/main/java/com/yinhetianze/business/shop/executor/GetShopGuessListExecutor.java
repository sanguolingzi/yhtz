package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.shop.model.ShopGuessModel;
import com.yinhetianze.business.shop.service.info.ShopGuessInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GetShopGuessListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopGuessInfoService shopGuessInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopGuessModel shopGuessModel=(ShopGuessModel) model;
        PageHelper.startPage(shopGuessModel.getCurrentPage(), shopGuessModel.getPageSize());
        PageInfo pageInfo=new PageInfo(shopGuessInfoServiceImpl.selectShopGuessList());
        //PageInfo pageInfo=new PageInfo(shopInfoServiceImpl.selectShopGuessList());
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
