package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.business.shopbrand.service.busi.ShopBrandBusiService;
import com.yinhetianze.business.shopbrand.service.info.ShopBrandInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.shop.BusiShopBrandPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 店铺修改品牌是否显示接口
 */
@Service
public class UpdateShopBrandIsShowExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private ShopBrandBusiService shopBrandBusiServiceImpl;

    @Autowired
    private ShopBrandInfoService shopBrandInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        BusiShopBrandModel busiShopBrandModel = (BusiShopBrandModel) model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(busiShopPojo == null)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);

        BusiShopBrandPojo busiShopBrandPojo = new BusiShopBrandPojo();
        busiShopBrandPojo.setId(busiShopBrandModel.getId());
        busiShopBrandPojo.setShopId(busiShopPojo.getId());
        busiShopBrandPojo=shopBrandInfoServiceImpl.selectOne(busiShopBrandPojo);
        if(busiShopPojo == null)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);

        BusiShopBrandPojo temp = new BusiShopBrandPojo();
        temp.setId(busiShopBrandPojo.getId());
        temp.setIsShow(busiShopBrandModel.getIsShow());
        shopBrandBusiServiceImpl.updateByPrimaryKeySelective(temp);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiShopBrandModel busiShopBrandModel = (BusiShopBrandModel) model;

        if(CommonUtil.isEmpty(busiShopBrandModel.getId())){
            errorMessage.rejectNull("id",null,"id");
            return  errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopBrandModel.getIsShow())){
            errorMessage.rejectNull("isShow",null,"是否显示");
            return  errorMessage;
        }


        if(CommonUtil.isEmpty(busiShopBrandModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return  errorMessage;
        }

        return errorMessage;
    }
}
