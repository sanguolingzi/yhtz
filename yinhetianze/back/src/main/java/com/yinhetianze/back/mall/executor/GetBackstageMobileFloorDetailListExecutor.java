package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.pojo.back.MobileFloorDetailPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorDetailInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class GetBackstageMobileFloorDetailListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MobileFloorDetailInfoService mobileFloorDetailInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MobileFloorDetailModel mobileFloorDetailModel=(MobileFloorDetailModel)model;
        MobileFloorDetailPojo mobileFloorDetailPojo=new MobileFloorDetailPojo();
        BeanUtils.copyProperties(mobileFloorDetailModel,mobileFloorDetailPojo);
        PageHelper.startPage(mobileFloorDetailModel.getCurrentPage(),mobileFloorDetailModel.getPageSize());
        PageInfo pageInfo=new PageInfo(mobileFloorDetailInfoServiceImpl.selectMobileFloorDetailList(mobileFloorDetailPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
