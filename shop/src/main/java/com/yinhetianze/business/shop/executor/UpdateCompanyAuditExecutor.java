package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.busi.ShopCompanyBusiService;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
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
 * 企业 修改审核信息
 */

@Component
public class UpdateCompanyAuditExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopCompanyBusiService shopCompanyBusiServiceImpl;

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopCompanyModel busiShopCompanyModel  =(BusiShopCompanyModel)model;

        BusiShopCompanyPojo dbEntity =  shopCompanyInfoServiceImpl.selectByCustomerId(busiShopCompanyModel.getCustomerId());
        if(dbEntity == null
                || dbEntity.getId().intValue() != busiShopCompanyModel.getId().intValue()){
            LoggerUtil.error(UpdateCompanyAuditExecutor.class,"dbEntity.getId():"+dbEntity.getId()+"...busiShopCompanyModel.getId():"+busiShopCompanyModel.getId());
            throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        int result = shopCompanyBusiServiceImpl.updateInfo(busiShopCompanyModel);
        if(result <= 0)
            throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiShopCompanyModel busiShopCompanyModel  =(BusiShopCompanyModel)model;


        if(CommonUtil.isEmpty(busiShopCompanyModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getId())){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getBusinessLicense())){
            errorMessage.rejectNull("businessLicense",null,"营业执照");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getCompanyPhoto())){
            errorMessage.rejectNull("companyPhoto",null,"企业门头照");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getIdCardP())){
            errorMessage.rejectNull("idCardP",null,"身份证正面");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getIdCardN())){
            errorMessage.rejectNull("idCardN",null,"身份证反面");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getLicenseCode())){
            errorMessage.rejectNull("licenseCode",null,"统一社会信用代码");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getLegalOwner())){
            errorMessage.rejectNull("legalOwner",null,"企业法人姓名");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getIdCard())){
            errorMessage.rejectNull("idcard",null,"企业法人身份证号");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getCompanyName())){
            errorMessage.rejectNull("companyName",null,"企业名称");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getRegionLocation())){
            errorMessage.rejectNull("regionLocation",null,"企业所在省份");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getCity())){
            errorMessage.rejectNull("city",null,"企业所在城市");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getAreaCounty())){
            errorMessage.rejectNull("areaCounty",null,"企业所在区域");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getAddress())){
            errorMessage.rejectNull("address",null,"企业详细地址");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        busiShopCompanyModel.setCustomerId(tokenUser.getId());

        return errorMessage;
    }
}
