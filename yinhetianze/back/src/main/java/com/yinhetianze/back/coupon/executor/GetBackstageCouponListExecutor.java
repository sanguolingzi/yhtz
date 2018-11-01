package com.yinhetianze.back.coupon.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.CouponPojo;
import com.yinhetianze.systemservice.coupon.model.CouponModel;
import com.yinhetianze.systemservice.coupon.service.info.CouponInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetBackstageCouponListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private CouponInfoService couponInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        CouponModel couponModel=(CouponModel)model;
        CouponPojo couponPojo=new CouponPojo();
        if(CommonUtil.isNotEmpty(couponModel.getCouponName())){
            try {
                String couponName = URLDecoder.decode(couponModel.getCouponName(), "UTF-8");
                couponPojo.setCouponName(couponName);
            }catch (Exception e){
                LoggerUtil.error(GetBackstageCouponListExecutor.class, e);
            }
        }
        PageHelper.startPage(couponModel.getCurrentPage(),couponModel.getPageSize());
        PageInfo pageInfo=new PageInfo(couponInfoServiceImpl.selectCouponList(couponPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
