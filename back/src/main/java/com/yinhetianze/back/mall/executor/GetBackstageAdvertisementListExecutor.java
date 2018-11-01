package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.AdvertisementPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementModel;
import com.yinhetianze.systemservice.mall.service.info.AdvertisementInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetBackstageAdvertisementListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private AdvertisementInfoService advertisementInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        AdvertisementModel advertisementModel=(AdvertisementModel)model;
        AdvertisementPojo advertisementPojo=new AdvertisementPojo();
        BeanUtils.copyProperties(advertisementModel,advertisementPojo);
        if(CommonUtil.isNotEmpty(advertisementModel.getAdvertisementName())){
            try{
                String advertisementName = URLDecoder.decode(advertisementModel.getAdvertisementName(),"UTF-8");
                advertisementModel.setAdvertisementName(advertisementName);
            }catch (Exception e){
                LoggerUtil.error(GetPcFloorProductListExecutor.class, e);
            }
        }
        PageHelper.startPage(advertisementModel.getCurrentPage(),advertisementModel.getPageSize());
        PageInfo pageInfo=new PageInfo(advertisementInfoServiceImpl.selectBackstageAdvertisementList(advertisementPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
