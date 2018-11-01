package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.MobileFloorDetailPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.busi.MobileFloorDetailBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteBackstageMobileFloorDetailExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MobileFloorDetailBusiService mobileFloorDetailBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MobileFloorDetailModel mobileFloorDetailModel=(MobileFloorDetailModel)model;
        MobileFloorDetailPojo mobileFloorDetailPojo=new MobileFloorDetailPojo();
        mobileFloorDetailPojo.setId(mobileFloorDetailModel.getId());
        mobileFloorDetailPojo.setDelFlag((short)1);
        int result=mobileFloorDetailBusiServiceImpl.updateByPrimaryKeySelective(mobileFloorDetailPojo);
        if(result<=0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        MobileFloorDetailModel mobileFloorDetailModel=(MobileFloorDetailModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(mobileFloorDetailModel.getId())){
            errorMessage.rejectNull("id",null,"ID");
        }
        return errorMessage;
    }
}
