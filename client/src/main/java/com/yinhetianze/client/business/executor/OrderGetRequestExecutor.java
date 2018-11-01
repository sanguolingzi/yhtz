package com.yinhetianze.client.business.executor;

import com.yinhetianze.core.business.client.AbstractClientExecutorGetAdapter;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class OrderGetRequestExecutor extends AbstractClientExecutorGetAdapter
{

    @Override
    protected String getServerName()
    {
        return applicationPropertiesUtil.getStrProp("server.order.spring.application.name");
    }

    @Override
    public ResponseData fusing(HttpServletRequest request, HttpServletResponse response, String pathParam)
    {
        // 记录日志
        LoggerUtil.warn(OrderGetRequestExecutor.class, "[{}] responses failed！failed message ：requestURI={}，params={}",
                new Object[]{applicationPropertiesUtil.getStrProp("server.order.spring.application.name"), request.getRequestURI(), CommonUtil.showRequestParams(request)});

        // 容错返回提示信息
        ResponseData data = new ResponseData();
        data.setResultInfo(new Result("Server is busy. Try again later please."));
        return data;
    }

    @Override
    protected String getServerContext()
    {
        return applicationPropertiesUtil.getStrProp("server.order.server.context-path", "/");
    }
}
