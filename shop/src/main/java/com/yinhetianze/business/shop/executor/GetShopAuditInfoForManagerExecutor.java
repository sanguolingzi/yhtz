package com.yinhetianze.business.shop.executor;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.shop.service.busi.impl.ShopCompanyBusiServiceImpl;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 后台管理 查询企业 店铺综合审核信息
 */

@Component
public class GetShopAuditInfoForManagerExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopAuditInfoService shopAuditInfoServiceImpl;

    @Autowired
    private AreaModelUtils areaModelUtils;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiSysShopAuditModel busiSysShopAuditModel = (BusiSysShopAuditModel)model;
        BusiSysShopAuditPojo busiSysShopAuditPojo = new BusiSysShopAuditPojo();
        busiSysShopAuditPojo.setCustomerId(busiSysShopAuditModel.getCustomerId());
        busiSysShopAuditPojo.setRelationId(busiSysShopAuditModel.getRelationId());
        busiSysShopAuditPojo.setAuditType(busiSysShopAuditModel.getAuditType());

        //查看企业
        if(busiSysShopAuditModel.getAuditType() == 2){
            List<BusiSysShopAuditPojo> list =  shopAuditInfoServiceImpl.select(busiSysShopAuditPojo);
            if(CommonUtil.isEmpty(list))
                return busiSysShopAuditModel;

            busiSysShopAuditPojo = list.get(0);
            BeanUtils.copyProperties(busiSysShopAuditPojo,busiSysShopAuditModel);
        //查找店铺信息
        }else if(busiSysShopAuditModel.getAuditType() == 1){
            if(busiSysShopAuditModel.getAuditStatus() != 0){
                List<BusiSysShopAuditPojo> list =  shopAuditInfoServiceImpl.select(busiSysShopAuditPojo);
                if(CommonUtil.isEmpty(list))
                    return busiSysShopAuditModel;

                busiSysShopAuditPojo = list.get(0);
                BeanUtils.copyProperties(busiSysShopAuditPojo,busiSysShopAuditModel);
            }else{
                //审核已通过 查找店铺信息
                BusiShopPojo busiShopPojo = new BusiShopPojo();
                busiShopPojo.setId(busiSysShopAuditModel.getRelationId());
                busiShopPojo.setCustomerId(busiSysShopAuditModel.getCustomerId());
                busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
                if(busiShopPojo != null){
                    JSONObject content = new JSONObject();
                    content.put("shopLogo",busiShopPojo.getShopLogo());
                    content.put("shopMainPhoto",busiShopPojo.getShopMainPhoto());
                    content.put("shopMemo",busiShopPojo.getShopMemo());
                    content.put("shopMainProduct",busiShopPojo.getShopMainProduct());
                    content.put("shopName",busiShopPojo.getShopName());
                    content.put("shopWapPhoto",busiShopPojo.getShopWapPhoto());
                    content.put("shopType",busiShopPojo.getShopType());
                    content.put("postageFree", busiShopPojo.getPostageFree());
                    content.put("shopPhone",busiShopPojo.getShopPhone());

                    busiSysShopAuditModel.setAuditContent(content.toJSONString());


                    JSONObject contentResult = new JSONObject();
                    contentResult.put("shopLogoResult",true);
                    contentResult.put("shopMainPhotoResult",true);
                    contentResult.put("shopMemoResult",true);
                    contentResult.put("shopMainProductResult",true);
                    contentResult.put("shopNameResult",true);
                    contentResult.put("shopWapPhotoResult",true);
                    contentResult.put("shopTypeResult",true);
                    contentResult.put("postageFreeResult", true);
                    contentResult.put("shopPhoneResult",true);
                    busiSysShopAuditModel.setAuditResultJson(contentResult.toJSONString());

                }
            }
        }



        try{
            //省份
            JSONObject content = CommonUtil.readFromString(busiSysShopAuditModel.getAuditContent(), JSONObject.class);
            AreaInfo province = areaModelUtils.getProvince(content.getString("regionLocation"),false);
            String provinceName = (province==null?"":province.getName());
            //城市
            AreaInfo city = areaModelUtils.getCity(content.getString("city"),false);
            String cityName = (city==null?"":city.getName());

            //区域
            AreaInfo areaCounty = areaModelUtils.getCounty(content.getString("areaCounty"));
            String areaCountyName =(areaCounty==null?"":areaCounty.getName());
            content.put("place",provinceName+cityName+areaCountyName);

            Short shopType = content.getShort("shopType");
            if(shopType!=null&&shopType==0){
                content.put("shopType","实体店铺");
            }else if(shopType!=null &&shopType==1){
                content.put("shopType","游戏店铺");
            }
            busiSysShopAuditModel.setAuditContent(content.toJSONString());
        }catch(Exception e){
            LoggerUtil.error(ShopCompanyBusiServiceImpl.class,"企业信息获取企业地址异常:"+e);
        }
        return busiSysShopAuditModel;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiSysShopAuditModel busiSysShopAuditModel = (BusiSysShopAuditModel)model;

        if(CommonUtil.isEmpty(busiSysShopAuditModel.getRelationId())){
            errorMessage.rejectNull("relationId",null,"relationId");
        }

        if(CommonUtil.isEmpty(busiSysShopAuditModel.getCustomerId())){
            errorMessage.rejectNull("customerId",null,"customerId");
        }

        if(CommonUtil.isEmpty(busiSysShopAuditModel.getAuditType())){
            errorMessage.rejectNull("auditType",null,"auditType");
        }
        return errorMessage;
    }
}
