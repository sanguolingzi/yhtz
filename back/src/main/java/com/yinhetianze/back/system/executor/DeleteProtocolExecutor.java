package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.ProtocolModel;
import com.yinhetianze.systemservice.system.service.busi.ProtocolBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class DeleteProtocolExecutor extends AbstractRestBusiExecutor<Object> {
    @Autowired
    private ProtocolBusiService protocolBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProtocolModel protocolModel=(ProtocolModel)model;
        int result=protocolBusiServiceImpl.deleteProtocol(protocolModel.getProtocolIds());
        if(result<1){
            throw new BusinessException("删除协议失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        ProtocolModel protocolModel=(ProtocolModel)model;
        if(CommonUtil.isEmpty(protocolModel.getProtocolIds())){
            error.rejectNull("protocolIds",null,"协议ID数组");
        }
        return error;
    }
}
