package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.ChannelPojo;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;
import com.yinhetianze.systemservice.mall.service.busi.SysFloorBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.SysFloorPojo;
import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增楼层
 */
@Component
public class AddSysFloorExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysFloorBusiService sysFloorBusiServiceImpl;

    @Autowired
    private SysFloorInfoService sysFloorInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = sysFloorBusiServiceImpl.addInfo((SysFloorModel)model);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
       SysFloorModel sysFloorModel = (SysFloorModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(sysFloorModel.getFloorName())){
            errorMessage.rejectError("floorName",null,"楼层名");
        }


        SysFloorPojo sysFloorPojo = new SysFloorPojo();
        sysFloorPojo.setFloorName(sysFloorModel.getFloorName());
        sysFloorPojo = sysFloorInfoServiceImpl.selectOne(sysFloorPojo);
        if(sysFloorPojo != null){
           errorMessage.rejectError("channelName","BC0055","楼层名称","楼层名称");
           return errorMessage;
        }

        if(CommonUtil.isEmpty(sysFloorModel.getSort())){
            errorMessage.rejectNull("sort",null,"排序");
            return errorMessage;
        }
        return errorMessage;
    }
}
