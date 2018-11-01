package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.cachedata.area.AreaInfo;
import com.yinhetianze.common.business.sys.area.cachedata.AreaModelUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 企业 查询基础信息
 */

@Component
public class GetCompanyInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private AreaModelUtils areaModelUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {



        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        BusiShopCompanyPojo busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectByCustomerId(tokenUser.getId());


        BusiShopCompanyModel busiShopCompanyModel = new BusiShopCompanyModel();
        if(busiShopCompanyPojo!=null){
            busiShopCompanyModel.setBusinessLicense(busiShopCompanyPojo.getBusinessLicense());
            busiShopCompanyModel.setIdCardN(busiShopCompanyPojo.getIdCardN());
            busiShopCompanyModel.setIdCardP(busiShopCompanyPojo.getIdCardP());
            busiShopCompanyModel.setIdCard(busiShopCompanyPojo.getIdCard());
            busiShopCompanyModel.setLegalOwner(busiShopCompanyPojo.getLegalOwner());
            busiShopCompanyModel.setLicenseCode(busiShopCompanyPojo.getLicenseCode());
            busiShopCompanyModel.setAuditStatus(busiShopCompanyPojo.getAuditStatus());
            busiShopCompanyModel.setId(busiShopCompanyPojo.getId());

            busiShopCompanyModel.setCompanyPhoto(busiShopCompanyPojo.getCompanyPhoto());
            busiShopCompanyModel.setCompanyName(busiShopCompanyPojo.getCompanyName());

            try{
               //省份
               AreaInfo province = areaModelUtils.getProvince(busiShopCompanyPojo.getRegionLocation(),false);
               busiShopCompanyModel.setRegionLocation(province==null?"":province.getName());

               //城市
               AreaInfo city = areaModelUtils.getCity(busiShopCompanyPojo.getCity(),false);
               busiShopCompanyModel.setCity(city==null?"":city.getName());

               //区域
               AreaInfo areaCounty = areaModelUtils.getCounty(busiShopCompanyPojo.getAreaCounty());
               busiShopCompanyModel.setAreaCounty(areaCounty==null?"":areaCounty.getName());

            }catch(Exception e){
                LoggerUtil.error(GetCompanyInfoExecutor.class,"企业信息获取企业地址异常:"+e);
            }
            busiShopCompanyModel.setAddress(busiShopCompanyPojo.getAddress());
        }


        return busiShopCompanyModel;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiShopCompanyModel busiShopCompanyModel = (BusiShopCompanyModel)model;
        if(CommonUtil.isEmpty(busiShopCompanyModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        return errorMessage;
    }
}
