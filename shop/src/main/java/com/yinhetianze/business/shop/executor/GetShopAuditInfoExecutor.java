package com.yinhetianze.business.shop.executor;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shopaudit.model.BusiSysShopAuditModel;
import com.yinhetianze.business.shopaudit.service.info.ShopAuditInfoService;
import com.yinhetianze.cachedata.area.AreaInfo;
import com.yinhetianze.common.business.sys.area.cachedata.AreaModelUtils;
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
 * 管理 查询企业 店铺综合审核信息
 */

@Component
public class GetShopAuditInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopAuditInfoService shopAuditInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private AreaModelUtils areaModelUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiSysShopAuditModel busiSysShopAuditModel = (BusiSysShopAuditModel)model;
        BusiSysShopAuditPojo busiSysShopAuditPojo = new BusiSysShopAuditPojo();
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        busiSysShopAuditPojo.setCustomerId(tokenUser.getId());
        busiSysShopAuditPojo.setId(busiSysShopAuditModel.getId());
        busiSysShopAuditPojo.setAuditType(busiSysShopAuditModel.getAuditType());

        busiSysShopAuditPojo =  shopAuditInfoServiceImpl.selectOne(busiSysShopAuditPojo);
        if(CommonUtil.isEmpty(busiSysShopAuditPojo))
           return null;

        BeanUtils.copyProperties(busiSysShopAuditPojo,busiSysShopAuditModel);

        try{
            JSONObject content = CommonUtil.readFromString(busiSysShopAuditModel.getAuditContent(), JSONObject.class);
            //省份
            AreaInfo province = areaModelUtils.getProvince(content.getString("regionLocation"),false);
            content.put("regionLocationName",(province==null?"":province.getName()));

            //城市
            AreaInfo city = areaModelUtils.getCity(content.getString("city"),false);
            content.put("cityName",(city==null?"":city.getName()));

            //区域
            AreaInfo areaCounty = areaModelUtils.getCounty(content.getString("areaCounty"));
            content.put("areaCountyName",(areaCounty==null?"":areaCounty.getName()));

            if(busiSysShopAuditModel.getAuditType() == 2
                    && content.get("shopId") == null){

                BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(busiSysShopAuditPojo.getCustomerId());
                if(busiShopPojo!=null){
                    content.put("shopId",busiShopPojo.getId());
                }
            }
            busiSysShopAuditModel.setAuditContent(content.toJSONString());
        }catch(Exception e){
            LoggerUtil.error(GetShopAuditInfoExecutor.class,e.getMessage());
        }



        return busiSysShopAuditModel;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiSysShopAuditModel busiSysShopAuditModel = (BusiSysShopAuditModel)model;

        if(CommonUtil.isEmpty(busiSysShopAuditModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
        }

        if(CommonUtil.isEmpty(busiSysShopAuditModel.getId())){
            errorMessage.rejectNull("id",null,"id");
        }

        if(CommonUtil.isEmpty(busiSysShopAuditModel.getAuditType())){
            errorMessage.rejectNull("auditType",null,"auditType");
        }

        return errorMessage;
    }
}
