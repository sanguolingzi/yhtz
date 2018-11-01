package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.MobileFloorPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorModel;
import com.yinhetianze.systemservice.mall.service.busi.MobileFloorBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteBackstageMobileFloorExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MobileFloorBusiService mobileFloorBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MobileFloorModel mobileFloorModel=(MobileFloorModel)model;
        MobileFloorPojo mobileFloorPojo=new MobileFloorPojo();
        mobileFloorPojo.setId(mobileFloorModel.getId());
        mobileFloorPojo.setDelFlag((short)1);
        int result=mobileFloorBusiServiceImpl.updateByPrimaryKeySelective(mobileFloorPojo);
        if(result<=0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        MobileFloorModel mobileFloorModel=(MobileFloorModel)model;
        if(CommonUtil.isEmpty(mobileFloorModel.getId())){
            errorMessage.rejectError("id",null,"ID");
        }
        return errorMessage;
    }
}
