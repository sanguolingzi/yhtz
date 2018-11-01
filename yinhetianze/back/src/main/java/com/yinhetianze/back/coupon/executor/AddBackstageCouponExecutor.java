package com.yinhetianze.back.coupon.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.CouponPojo;
import com.yinhetianze.systemservice.coupon.model.CouponModel;
import com.yinhetianze.systemservice.coupon.service.busi.CouponbusiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AddBackstageCouponExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private CouponbusiService couponbusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        CouponModel couponModel=(CouponModel)model;
        CouponPojo couponPojo=new CouponPojo();
        BeanUtils.copyProperties(couponModel,couponPojo);
        int result =couponbusiServiceImpl.insertSelective(couponPojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        CouponModel couponModel=(CouponModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(couponModel.getCouponName())){
            errorMessage.rejectNull("couponName","BC0055","优惠券名称");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(couponModel.getCouponAmount())){
            errorMessage.rejectNull("couponAmount","BC0055","优惠券金额");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(couponModel.getIsDeadlines())){
            errorMessage.rejectNull("isDeadlines","BC0055","优惠券是否有期限");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(couponModel.getCouponCate())){
            errorMessage.rejectNull("couponCate","BC0055","优惠券分类");
            return errorMessage;
        }
        return errorMessage;
    }
}
