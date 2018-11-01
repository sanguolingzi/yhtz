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
public class AddBackstageMobileFloorExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private MobileFloorBusiService mobileFloorBusiServiceImpl;

    @Autowired
    private MobileFloorInfoService mobileFloorInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result=mobileFloorBusiServiceImpl.addInfo((MobileFloorModel)model);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        MobileFloorModel mobileFloorModel=(MobileFloorModel)model;
        if(CommonUtil.isEmpty(mobileFloorModel.getMobileFloorName())){
            errorMessage.rejectError("mobileFloorName",null,"楼层名");
        }
        MobileFloorPojo mobileFloorPojo=new MobileFloorPojo();
        mobileFloorPojo.setMobileFloorName(mobileFloorModel.getMobileFloorName());
        mobileFloorPojo.setDelFlag((short)0);
        mobileFloorPojo=mobileFloorInfoServiceImpl.selectOne(mobileFloorPojo);
        if(mobileFloorPojo!=null){
            errorMessage.rejectError("mobileFloorName","BC0055","楼层名称已存在","楼层名称已存在");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(mobileFloorModel.getSort())){
            errorMessage.rejectError("sort",null,"排序");
        }
        if(CommonUtil.isEmpty(mobileFloorModel.getIsShow())){
            errorMessage.rejectError("isShow",null,"是否显示");
        }
        if(CommonUtil.isEmpty(mobileFloorModel.getLinkMarkup())){
            errorMessage.rejectError("linkMarkup",null,"链接标记");
        }
        if (CommonUtil.isEmpty(mobileFloorModel.getPhotoUrl())){
            errorMessage.rejectNull("photoUrl",null,"图片路径");
        }
        return errorMessage;
    }
}
