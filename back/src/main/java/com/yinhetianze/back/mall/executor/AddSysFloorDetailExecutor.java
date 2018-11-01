package com.yinhetianze.back.mall.executor;

import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.busi.SysFloorDetailBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增楼层详情
 */
@Component
public class AddSysFloorDetailExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysFloorDetailBusiService sysFloorDetailBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = sysFloorDetailBusiServiceImpl.addInfo( (SysFloorDetailModel)model);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysFloorDetailModel sysFloorDetailModel = (SysFloorDetailModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(sysFloorDetailModel.getContentId())){
            errorMessage.rejectNull("contentId",null,"关联数据Id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(sysFloorDetailModel.getContentType())){
           errorMessage.rejectNull("contentType",null,"类型");
           return errorMessage;
        }

        if(CommonUtil.isEmpty(sysFloorDetailModel.getFloorId())){
           errorMessage.rejectNull("floorId",null,"所属楼层id");
           return errorMessage;
        }

        if(CommonUtil.isEmpty(sysFloorDetailModel.getPhotoUrl())){
           errorMessage.rejectNull("photoUrl",null,"图片Url");
           return errorMessage;
        }

        if(CommonUtil.isEmpty(sysFloorDetailModel.getIsShow())){
           errorMessage.rejectNull("isShow",null,"是否显示");
           return errorMessage;
        }
        if(CommonUtil.isEmpty(sysFloorDetailModel.getSort())){
           errorMessage.rejectNull("sort",null,"排序");
           return errorMessage;
        }

        return errorMessage;
    }
}
