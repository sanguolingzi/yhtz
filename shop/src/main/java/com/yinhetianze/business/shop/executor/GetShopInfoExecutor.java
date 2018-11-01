package com.yinhetianze.business.shop.executor;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shopaudit.service.info.ShopAuditInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 店铺 查询基础信息
 */

@Component
public class GetShopInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private ShopAuditInfoService shopAuditInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(busiShopPojo == null){
            return null;
        }


        //店铺为审核待审核状态 显示待审核信息
        BusiShopModel busiShopModel = new BusiShopModel();
        if(busiShopPojo.getAuditStatus() == 2
                && "showAuditInfo".equals(model.getModelName())){

            BusiSysShopAuditPojo busiSysShopAuditPojo = new BusiSysShopAuditPojo();
            busiSysShopAuditPojo.setCustomerId(tokenUser.getId());
            busiSysShopAuditPojo.setRelationId(busiShopPojo.getId());
            busiSysShopAuditPojo.setAuditType((short)1);
            List<BusiSysShopAuditPojo> list =  shopAuditInfoServiceImpl.select(busiSysShopAuditPojo);

            if(CommonUtil.isEmpty(list))
                return null;

            busiSysShopAuditPojo = list.get(0);
            try{
                JSONObject content = CommonUtil.readFromString(busiSysShopAuditPojo.getAuditContent(),JSONObject.class);
                busiShopModel.setShopLogo(content.getString("shopLogo"));
                busiShopModel.setShopType(content.getShort("shopType"));
                busiShopModel.setShopMainProduct(content.getString("shopMainProduct"));
                busiShopModel.setShopMemo(content.getString("shopMemo"));
                busiShopModel.setShopName(content.getString("shopName"));
                busiShopModel.setShopPhone(content.getString("shopPhone"));
                busiShopModel.setPhoneShow(content.getShort("phoneShow"));
                busiShopModel.setShopDecorate(busiShopPojo.getShopDecorate());
                busiShopModel.setId(busiShopPojo.getId());
                busiShopModel.setAuditStatus(busiShopPojo.getAuditStatus());
                busiShopModel.setPostageFree(busiShopPojo.getPostageFree());
                busiShopModel.setShopMainPhoto(content.getString("shopMainPhoto"));
                busiShopModel.setShopWapPhoto(content.getString("shopWapPhoto"));
            }catch(Exception e){
                LoggerUtil.error(GetShopInfoExecutor.class,e.getMessage());
            }
            return busiShopModel;
        }
        BeanUtils.copyProperties(busiShopPojo,busiShopModel);
        return busiShopModel;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiShopModel busiShopModel = (BusiShopModel)model;
        if(CommonUtil.isEmpty(busiShopModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        return errorMessage;
    }
}
