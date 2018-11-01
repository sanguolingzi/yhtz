package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.pojo.back.AdvertisementDetailPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementDetailModel;
import com.yinhetianze.systemservice.mall.service.info.AdvertisementDetailInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GetBackstageAdvertisementDetailListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private AdvertisementDetailInfoService advertisementDetailInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        AdvertisementDetailModel advertisementDetailModel=(AdvertisementDetailModel)model;
        AdvertisementDetailPojo advertisementDetailPojo=new AdvertisementDetailPojo();
        BeanUtils.copyProperties(advertisementDetailModel,advertisementDetailPojo);
        PageHelper.startPage(advertisementDetailModel.getCurrentPage(),advertisementDetailModel.getPageSize());
        PageInfo pageInfo=new PageInfo(advertisementDetailInfoServiceImpl.selectBackstageAdvertisementDetailList(advertisementDetailPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }

}
