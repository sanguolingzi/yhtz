package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.service.info.CustomerCollectorInfoService;
import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.cachedata.area.AreaInfo;
import com.yinhetianze.common.business.sys.area.cachedata.AreaModelUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  查询公众浏览-店铺首页
 */

@Component
public class GetShopIndexExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private CustomerCollectorInfoService customerCollectorInfoServiceImpl;

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private AreaModelUtils areaModelUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopModel busiShopModel = (BusiShopModel)model;

        BusiShopPojo busiShopPojo = new BusiShopPojo();
        busiShopPojo.setId(busiShopModel.getId());
        busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
        if(busiShopPojo == null)
            return null;

        BusiCustomerCollectorModel busiCustomerCollectorModel = new BusiCustomerCollectorModel();
        busiCustomerCollectorModel.setcType((short)1);
        busiCustomerCollectorModel.setRelationId(busiShopPojo.getId());
        Integer shopCollectCount = customerCollectorInfoServiceImpl.selectCount(busiCustomerCollectorModel);
        busiShopModel.setShopCollectCount(shopCollectCount);
        busiShopModel.setShopLogo(busiShopPojo.getShopLogo());
        busiShopModel.setShopMemo(busiShopPojo.getShopMemo());
        busiShopModel.setShopDecorate(busiShopPojo.getShopDecorate());
        busiShopModel.setShopWapPhoto(busiShopPojo.getShopWapPhoto());
        busiShopModel.setShopMainPhoto(busiShopPojo.getShopMainPhoto());
        busiShopModel.setShopName(busiShopPojo.getShopName());
        busiShopModel.setShopQrcode(busiShopPojo.getShopQrcode());
        busiShopModel.setCreateTime(busiShopPojo.getCreateTime());

        BusiShopCompanyPojo busiShopCompanyPojo = new BusiShopCompanyPojo();
        busiShopCompanyPojo.setId(busiShopPojo.getCompanyinfoId());
        busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectOne(busiShopCompanyPojo);
        if(busiShopCompanyPojo != null){
            try{
                //省份
                AreaInfo province = areaModelUtils.getProvince(busiShopCompanyPojo.getRegionLocation(),false);
                String regionLocationName=(province==null?"":province.getName());

                //城市
                AreaInfo city = areaModelUtils.getCity(busiShopCompanyPojo.getCity(),false);
                String cityName=(city==null?"":city.getName());

                //区域
                AreaInfo areaCounty = areaModelUtils.getCounty(busiShopCompanyPojo.getAreaCounty());
                String areaCountyName=(areaCounty==null?"":areaCounty.getName());
                busiShopModel.setPlace(regionLocationName+cityName+areaCountyName);

                busiShopModel.setBusinessLicense(busiShopCompanyPojo.getBusinessLicense());
            }catch(Exception e){
                LoggerUtil.error(GetShopAuditInfoExecutor.class,e.getMessage());
            }
        }

        return busiShopModel;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiShopModel busiShopModel = (BusiShopModel)model;
        if(CommonUtil.isEmpty(busiShopModel.getId())){
            errorMessage.rejectNull("id",null,"店铺id");
            return errorMessage;
        }
        return errorMessage;
    }
}
