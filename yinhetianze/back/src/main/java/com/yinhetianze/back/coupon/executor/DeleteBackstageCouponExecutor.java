package com.yinhetianze.back.coupon.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.CouponPojo;
import com.yinhetianze.systemservice.coupon.service.busi.CouponbusiService;
import com.yinhetianze.systemservice.system.model.DeleteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteBackstageCouponExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private CouponbusiService couponbusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel deleteModel=(DeleteModel)model;
        String ids=deleteModel.getIds();
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            CouponPojo couponPojo=new CouponPojo();
            couponPojo.setId(Integer.parseInt(id));
            couponPojo.setDelFlag((short)1);
            int result = couponbusiServiceImpl.updateByPrimaryKeySelective(couponPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        DeleteModel deleteModel=(DeleteModel)model;
        if(CommonUtil.isEmpty(deleteModel.getIds())){
            errorMessage.rejectNull("ids",null,"ids");
            return errorMessage;
        }
        return errorMessage;
    }
}
