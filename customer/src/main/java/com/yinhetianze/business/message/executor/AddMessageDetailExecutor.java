package com.yinhetianze.business.message.executor;

import com.yinhetianze.business.message.model.BusiMessageDetailModel;
import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增消息详情信息
 */

@Component
public class AddMessageDetailExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private MessageDetailBusiService messageDetailBusiServiceImpl;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiMessageDetailModel busiMessageDetailModel = (BusiMessageDetailModel)model;
        BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
        BeanUtils.copyProperties(busiMessageDetailModel,busiMessageDetailPojo);
        int result = messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiMessageDetailModel busiMessageDetailModel = (BusiMessageDetailModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(busiMessageDetailModel.getMessageId())){
            errorMessage.rejectNull("message",null,"用户id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiMessageDetailModel.getmType())){
            errorMessage.rejectNull("mType",null,"消息类型");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiMessageDetailModel.getmContent())){
            errorMessage.rejectNull("mContent",null,"消息内容");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiMessageDetailModel.getmTitle())){
            errorMessage.rejectNull("mTitle",null,"消息标题");
            return errorMessage;
        }
        return errorMessage;
    }
}
