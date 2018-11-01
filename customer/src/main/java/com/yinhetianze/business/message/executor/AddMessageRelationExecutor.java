package com.yinhetianze.business.message.executor;

import com.yinhetianze.business.message.model.BusiMessageModel;
import com.yinhetianze.business.message.service.busi.MessageBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 建立用户与消息的关系
 */

@Component
public class AddMessageRelationExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private MessageBusiService messageBusiServiceImpl;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
        BeanUtils.copyProperties(model,busiMessagePojo);
        int result = messageBusiServiceImpl.addInfo(busiMessagePojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiMessageModel busiMessageModel = (BusiMessageModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(busiMessageModel.getId())){
            errorMessage.rejectNull("id",null,"用户id");
            return errorMessage;
        }
        return errorMessage;
    }
}
