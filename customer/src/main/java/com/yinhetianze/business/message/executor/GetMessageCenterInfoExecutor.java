package com.yinhetianze.business.message.executor;

import com.yinhetianze.business.message.model.BusiMessageCenterModel;
import com.yinhetianze.business.message.service.info.MessageDetailInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取 个人消息 汇总信息 消息未读数 等等
 */

@Component
public class GetMessageCenterInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private MessageDetailInfoService messageDetailInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiMessageCenterModel busiMessageCenterModel = (BusiMessageCenterModel)model;
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiMessageCenterModel.getToken());
        if(tokenUser!=null){
            busiMessageCenterModel.setMessageId(tokenUser.getId());
            return messageDetailInfoServiceImpl.selectMessageCenterInfo(busiMessageCenterModel);
        }else{
            //首页有这个接口 不能做权限拦截 所以这里需要做这个处理 在没有token的情况下 需要构造一些数据返回
            Map<String,Object> m = new HashMap<String,Object>();
            BusiMessageCenterModel info = new BusiMessageCenterModel();
            info.setMessageCount(0);
            m.put("notify",info);

            BusiMessageCenterModel logistic = new BusiMessageCenterModel();
            logistic.setMessageCount(0);
            m.put("logistic",logistic);
            return m;
        }

    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        return null;
    }
}
