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
import com.yinhetianze.systemservice.mall.service.info.MobileFloorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class UpdateBackstageMobileFloorExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MobileFloorBusiService mobileFloorBusiServiceImpl;

    @Autowired
    private MobileFloorInfoService mobileFloorInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result=mobileFloorBusiServiceImpl.updateInfo((MobileFloorModel)model);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        MobileFloorModel mobileFloorModel=(MobileFloorModel)model;
        if(CommonUtil.isEmpty(mobileFloorModel.getId())){
            errorMessage.rejectError("id",null,"ID");
        }
        if(CommonUtil.isEmpty(mobileFloorModel.getMobileFloorName())){
            errorMessage.rejectError("mobileFloorName",null,"楼层名");
        }
        MobileFloorPojo mobileFloorPojo=new MobileFloorPojo();
        mobileFloorPojo.setMobileFloorName(mobileFloorModel.getMobileFloorName());
        mobileFloorPojo.setDelFlag((short)0);
        mobileFloorPojo=mobileFloorInfoServiceImpl.selectOne(mobileFloorPojo);
        if(mobileFloorPojo!=null && mobileFloorModel.getId().intValue()!=mobileFloorPojo.getId().intValue()){
            errorMessage.rejectError("mobileFloorName","BC0055","楼层名称已存在","楼层名称已存在");
            return errorMessage;
        }
        return errorMessage;
    }
}
