package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.ProtocolModel;
import com.yinhetianze.systemservice.system.service.busi.ProtocolBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.ProtocolPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Service
public class UpdateProtocolExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private ProtocolBusiService protocolBusiServiceImpl;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProtocolPojo protocolPojo=new ProtocolPojo();
        BeanUtils.copyProperties(model,protocolPojo);
        if(CommonUtil.isNotEmpty(protocolPojo.getProtocolContent())){
            String s = new String(jodd.util.Base64.decode(protocolPojo.getProtocolContent()));
            protocolPojo.setProtocolContent(s);
        }
        int result=protocolBusiServiceImpl.updateByPrimaryKeySelective(protocolPojo);
        if(result<1){
            throw new BusinessException("更新协议失败");
        }
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        ProtocolModel protocolModel=(ProtocolModel)model;
        if(CommonUtil.isEmpty(protocolModel.getId())){
            error.rejectNull("id",null,"协议ID");
        }
        return error;
    }

}
