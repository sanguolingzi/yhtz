package com.yinhetianze.business.shop.executor;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.busi.ShopCompanyBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 企业 新增基础信息
 */

@Component
public class AddCompanyInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopCompanyBusiService shopCompanyBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopCompanyModel busiShopCompanyModel = (BusiShopCompanyModel)model;
        int addShop = shopCompanyBusiServiceImpl.addInfo(busiShopCompanyModel);

        if(addShop > 0){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result","success");
            jsonObject.put("data",addShop);
            return jsonObject;
        }
        throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiShopCompanyModel busiShopCompanyModel = (BusiShopCompanyModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(busiShopCompanyModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
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


        if(CommonUtil.isEmpty(busiShopCompanyModel.getCustomerId())){
            errorMessage.rejectNull("customerId",null,"用户id");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(tokenUser.getId().intValue() != busiShopCompanyModel.getCustomerId().intValue()){
            LoggerUtil.error(AddCompanyInfoExecutor.class,"tokenId:"+tokenUser.getId()+".....param CustomerId:"+busiShopCompanyModel.getCustomerId());
            errorMessage.rejectNull("info",null,"提交参数异常");
            return errorMessage;
        }
        return errorMessage;
    }
}
