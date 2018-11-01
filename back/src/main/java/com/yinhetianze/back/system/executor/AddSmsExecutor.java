package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.SmsModel;
import com.yinhetianze.systemservice.system.service.busi.SmsBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.SmsPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AddSmsExecutor extends AbstractRestBusiExecutor<String> {
    @Autowired
    private SmsBusiService smsBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SmsPojo smsPojo=new SmsPojo();
        BeanUtils.copyProperties(model,smsPojo);
        int result=smsBusiServiceImpl.addSms(smsPojo);
        if(result!=1){
            throw new BusinessException("添加短信模板失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        SmsModel smsModel=(SmsModel)model;
        if(CommonUtil.isEmpty(smsModel.getSmsTitle())){
            errorMessage.rejectNull("smsTitle",null,"短信模板标题");
        }
        if(CommonUtil.isEmpty(smsModel.getSmsContent())){
            errorMessage.rejectNull("smsContent",null,"短信模板文案");
        }
        return errorMessage;
    }
}
