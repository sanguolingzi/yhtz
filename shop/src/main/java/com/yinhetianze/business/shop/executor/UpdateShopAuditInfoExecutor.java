package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.busi.ShopBusiService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 店铺 修改入驻信息
 */

@Component
public class UpdateShopAuditInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopBusiService shopBusiServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopModel busiShopModel  =(BusiShopModel)model;
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(busiShopModel.getCustomerId());
        if(busiShopPojo == null
                || busiShopPojo.getId().intValue() != busiShopModel.getId().intValue()){
            LoggerUtil.error(UpdateShopAuditInfoExecutor.class,"数据信息不一致 dbid:"+busiShopPojo.getId()+"....提交id:"+busiShopModel.getId());
            throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }


        BeanUtils.copyProperties(model,busiShopPojo);
        int result = shopBusiServiceImpl.updateShopInfo(busiShopPojo);
        if(result <= 0)
            throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiShopModel busiShopModel  =(BusiShopModel)model;

        if(CommonUtil.isEmpty(busiShopModel.getId())){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopModel.getShopLogo())){
            errorMessage.rejectNull("shopLogo",null,"店铺主图");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopModel.getShopName())){
            errorMessage.rejectNull("shopName",null,"店铺名称");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopModel.getShopType())){
            errorMessage.rejectNull("shopType",null,"店铺类型");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopModel.getShopMainProduct())){
            errorMessage.rejectNull("shopMainProduct",null,"店铺主营商品");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopModel.getShopMemo())){
            errorMessage.rejectNull("shopMemo",null,"店铺简介");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopModel.getShopPhone())){
            errorMessage.rejectNull("shopPhone",null,"店铺电话");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopModel.getPhoneShow())){
            errorMessage.rejectNull("phoneShow",null,"是否显示电话");
            return errorMessage;
        }
        /*
        if(CommonUtil.isEmpty(busiShopModel.getPostageFree())){
            errorMessage.rejectNull("postageFree",null,"订单包邮金额");
            return errorMessage;
        }
        */

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        busiShopModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }
}
