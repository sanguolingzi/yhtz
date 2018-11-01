package com.yinhetianze.back.system.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.system.model.SysMemberInfoModel;
import com.yinhetianze.systemservice.system.service.busi.SysMemberBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AddChildSysMemberExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysMemberBusiService sysMemberBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysMemberInfoModel sysMemberInfoModel=(SysMemberInfoModel)model;
        int result = sysMemberBusiServiceImpl.addChildSysMember(sysMemberInfoModel);
        if(result <= 0){
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        SysMemberInfoModel sysMemberInfoModel=(SysMemberInfoModel)model;

        if(CommonUtil.isEmpty(sysMemberInfoModel.getMemberTitle())){
            errorMessage.rejectNull("classifyName",null,"权益标题");
        }
        if(CommonUtil.isEmpty(sysMemberInfoModel.getMemberContent())){
            errorMessage.rejectNull("content",null,"权益内容");
        }
        if(CommonUtil.isEmpty(sysMemberInfoModel.getMemberImg())){
            errorMessage.rejectNull("classifyImg",null,"权益主图");
        }

        if(CommonUtil.isEmpty(sysMemberInfoModel.getParentId())){
            errorMessage.rejectNull("parentId",null,"所属权益");
        }
        return errorMessage;
    }
}
