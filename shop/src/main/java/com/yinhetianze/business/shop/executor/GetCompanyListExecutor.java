package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.model.BusiShopCompanyPageModel;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 企业 查询企业列表
 */

@Component
public class GetCompanyListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopCompanyPageModel  busiShopCompanyPageModel  = (BusiShopCompanyPageModel)model;

        if(busiShopCompanyPageModel.getIsAll() == null){
            PageHelper.startPage(busiShopCompanyPageModel.getCurrentPage(),busiShopCompanyPageModel.getPageSize());
        }

        if(CommonUtil.isNotEmpty(busiShopCompanyPageModel.getCompanyName())){
            try{
                String companyName = URLDecoder.decode( busiShopCompanyPageModel.getCompanyName(), "UTF-8");
                busiShopCompanyPageModel.setCompanyName(companyName);
            }catch(Exception e){
                LoggerUtil.error(GetCompanyListExecutor.class,e.getMessage());
            }
        }
        PageInfo<BusiShopCompanyModel> pageInfo = new PageInfo( shopCompanyInfoServiceImpl.selectList(busiShopCompanyPageModel));
        PageData<BusiShopCompanyModel> pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
