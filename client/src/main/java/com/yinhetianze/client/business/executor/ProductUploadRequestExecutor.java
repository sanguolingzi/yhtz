package com.yinhetianze.client.business.executor;

import com.yinhetianze.core.business.client.AbstractClientExecutorPostAdapter;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ProductUploadRequestExecutor extends AbstractClientExecutorPostAdapter
{
    @Override
    public HttpEntity<?> handleRequestEntity(HttpServletRequest request, String uri) throws Exception
    {
        return handleFileUploadRequestEntity(request, uri);
    }

    @Override
    protected String getServerName()
    {
        return applicationPropertiesUtil.getStrProp("server.product.spring.application.name", "customer-server");
    }

    @Override
    protected String getServerContext()
    {
        return applicationPropertiesUtil.getStrProp("server.product.server.context-path", "/customer");
    }

    @Override
    public ResponseData fusing(HttpServletRequest request, HttpServletResponse response, String pathParam)
    {
        // 记录日志
        LoggerUtil.warn(CustomerGetRequestExecutor.class, "[{}] responses failed！failed message ：requestURI={}，params={}",
                new Object[]{applicationPropertiesUtil.getStrProp("server.product.spring.application.name"), request.getRequestURI(), CommonUtil.showRequestParams(request)});

        // 容错返回提示信息
        ResponseData data = new ResponseData();
        data.setResultInfo(new Result("Server is busy. Try again later please."));
        return data;
    }
}
