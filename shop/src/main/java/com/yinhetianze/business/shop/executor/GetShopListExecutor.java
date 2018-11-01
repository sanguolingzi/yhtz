package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.shop.model.BusiShopPageModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;

/**
 * 查询 店铺列表
 */

@Component
public class GetShopListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopPageModel busiShopPageModel = (BusiShopPageModel)model;
        if(CommonUtil.isNotEmpty(busiShopPageModel.getShopName())){
            try {
                String shopName= URLDecoder.decode(busiShopPageModel.getShopName(),"UTF-8");
                busiShopPageModel.setShopName(shopName);
            }catch (Exception e){
                LoggerUtil.error(GetShopListExecutor.class, e);
            }
        }
        PageHelper.startPage(busiShopPageModel.getCurrentPage(),busiShopPageModel.getPageSize());
        List<BusiShopPageModel> list = shopInfoServiceImpl.selectList(busiShopPageModel);
        PageInfo<BusiShopPageModel> pageInfo = new PageInfo(list);
        PageData<BusiShopPageModel> pageData = new PageData<>(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiShopPageModel busiShopPageModel = (BusiShopPageModel)model;
        if(CommonUtil.isNotEmpty(busiShopPageModel.getShopName())){
            try{
                String shopName = URLDecoder.decode( busiShopPageModel.getShopName(), "UTF-8");
                busiShopPageModel.setShopName(shopName);
            }catch(Exception e){
                LoggerUtil.error(GetCompanyListExecutor.class,e.getMessage());
            }
        }

        return null;
    }
}
