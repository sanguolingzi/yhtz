package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.business.shopbrand.service.busi.ShopBrandBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
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
 * 店铺提交品牌接口
 */
@Service
public class AddBrandForShopExecutor extends AbstractRestBusiExecutor<String>
{


    @Autowired
    private ShopBrandBusiService shopBrandBusiServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        BusiShopBrandModel busiShopBrandModel = (BusiShopBrandModel) model;
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiShopBrandModel.getToken());
        BusiShopPojo BusiShop = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(BusiShop == null){
            throw new BusinessException("BC0001","店铺");
        }

        busiShopBrandModel.setShopId(BusiShop.getId());
        int result = shopBrandBusiServiceImpl.addInfo(busiShopBrandModel);
        if(result <= 0){
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiShopBrandModel busiShopBrandModel = (BusiShopBrandModel) model;

        if(CommonUtil.isEmpty(busiShopBrandModel.getAddSelf())){
            errorMessage.rejectNull("addSelf", null,"是否自己添加标记");
            return errorMessage;
        }

        if(busiShopBrandModel.getAddSelf() == 1){
            if(CommonUtil.isEmpty(busiShopBrandModel.getBrandId()))
            errorMessage.rejectNull("brandId", null,"所选品牌id");
            return errorMessage;
        }



        if (CommonUtil.isEmpty(busiShopBrandModel.getShopBrandImg()))
        {
            errorMessage.rejectNull("shopBrandImg", null,"品牌图片");
            return errorMessage;
        }

        // 品牌名称不能为空
        if (CommonUtil.isEmpty(busiShopBrandModel.getBrandName()))
        {
            errorMessage.rejectNull("brandName", null,"品牌名称");
            return errorMessage;
        }

        // 排序号不能为空
        if (CommonUtil.isEmpty(busiShopBrandModel.getSort()))
        {
            errorMessage.rejectNull("sort", null,"排序号");
            return errorMessage;
        }



        if(CommonUtil.isEmpty(busiShopBrandModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return  errorMessage;
        }

        return errorMessage;
    }
}
