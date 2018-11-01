package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.ShopCategoryModel;
import com.yinhetianze.business.shop.service.busi.ShopCategoryBusiService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 增加店铺内分类
 */
@Service
public class UpdateShopCategoryExecutor extends AbstractRestBusiExecutor<String>{
    @Autowired
    private ShopCategoryBusiService shopCategoryBusiServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ShopCategoryModel shopCategoryModel = (ShopCategoryModel) model;
        shopCategoryBusiServiceImpl.updateInfo(shopCategoryModel);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage = new ErrorMessage();
        ShopCategoryModel shopCategoryModel = (ShopCategoryModel) model;
        if (CommonUtil.isEmpty(shopCategoryModel.getJsonString())) {
            errorMessage.rejectNull("jsonString", null, "提交参数有误");
            return errorMessage;
        }
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(busiShopPojo == null){
            errorMessage.rejectNull("info", null, "店铺信息");
            return errorMessage;
        }
        return errorMessage;
    }
}
