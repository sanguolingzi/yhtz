package com.yinhetianze.client.business.executor;

import com.yinhetianze.core.business.client.AbstractClientExecutorGetAdapter;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Service
public class ProductGetRequestExecutor extends AbstractClientExecutorGetAdapter
{

    /*@Override
    public HttpEntity<?> handleRequestEntity(HttpServletRequest request, String url)
    {
        RequestEntity.BodyBuilder builder = RequestEntity.method(HttpMethod.GET, URI.create(url));

        // 处理请求头参数
        builder = requestHeadParams(request, builder); // 处理前端页面设置的请求头
        builder = customHeadParams(request, builder); // 处理客户端自定义封装请求头

        return builder.build();
    }*/

    @Override
    protected String getServerName()
    {
        return applicationPropertiesUtil.getStrProp("server.product.spring.application.name", "product-server");
    }

    @Override
    public ResponseData fusing(HttpServletRequest request, HttpServletResponse response, String pathParam)
    {
        // 记录日志
        LoggerUtil.warn(ProductGetRequestExecutor.class, "[{}] responses failed！failed message ：requestURI={}，params={}",
                new Object[]{applicationPropertiesUtil.getStrProp("server.product.spring.application.name"), request.getRequestURI(), CommonUtil.showRequestParams(request)});

        // 容错返回提示信息
        ResponseData data = new ResponseData();
        data.setResultInfo(new Result("Server is busy. Try again later please."));
        return data;
    }

    @Override
    protected String getServerContext()
    {
        return applicationPropertiesUtil.getStrProp("server.product.server.context-path", CommonConstant.CHAR_SLASH);
    }
}
