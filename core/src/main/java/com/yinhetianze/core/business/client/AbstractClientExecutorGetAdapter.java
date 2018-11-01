package com.yinhetianze.core.business.client;

import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.security.custom.util.CustomSecurityHeader;
import org.apache.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端Get请求适配器
 * @param <T>
 */
public abstract class AbstractClientExecutorGetAdapter<T> extends AbstractClientExecutor<T>
{

    @Override
    protected String handleUriParamsUrl(HttpServletRequest request, StringBuffer serverUrl)
    {
        if (CommonUtil.isNotEmpty(request) && CommonUtil.isNotEmpty(serverUrl))
        {
            Enumeration<String> paramNames = request.getParameterNames();
            String name = null;
            StringBuffer temp = new StringBuffer();
            while (paramNames.hasMoreElements())
            {
                name = paramNames.nextElement();
                temp.append(name).append(CommonConstant.CHAR_EQ);
                try
                {
                    if (CommonUtil.isNotEmpty(request.getParameter(name)))
                    {
                        // 使用urlencode进行转码
//                        temp.append(URLEncoder.encode(request.getParameter(name), "UTF-8"));
                        // 不使用urlencode进行转码
                        temp.append(request.getParameter(name));
                    }
                }
                catch (Exception e)
                {
                    LoggerUtil.error(AbstractClientExecutorGetAdapter.class, e);
                }
                temp.append(CommonConstant.CHAR_AND);
            }

            if (temp.length() > 0)
            {
                temp.deleteCharAt(temp.length() - 1);
                try
                {
                    serverUrl.append(CommonConstant.CHAR_QUESTION).append(temp);
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e);
                }
            }
        }

        return serverUrl.toString();
    }

    @Override
    public HttpEntity<?> handleRequestEntity(HttpServletRequest request, String url)
    {
        HttpHeaders headers = new HttpHeaders();
        requestHeadParams(request, headers);
        customHeadParams(request, headers);
        LoggerUtil.info(AbstractClientExecutorGetAdapter.class, "Request Headers : [{}]", new Object[]{headers.toString()});

        RequestEntity entity = new RequestEntity(headers, HttpMethod.GET, URI.create(url));
        return entity;
    }

    /**
     * 执行接口调用
     * @param serverUrl
     * @param entity
     * @return
     */
    public ResponseData<T> executeInvoke(String serverUrl, HttpEntity<?> entity, Object...uriParams)
    {
        try
        {
            ResponseEntity<ResponseData> respEntity = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, ResponseData.class, uriParams);
            return handleResponseData(respEntity);
        }
        // http错误，意味着在过滤器中校验时返回的请求错误
        catch (HttpClientErrorException e)
        {
            ResponseData data = new ResponseData();
            Result result = new Result(String.valueOf(e.getStatusCode()), e.getStatusCode().getReasonPhrase());
            if (ResponseConstant.SC_SESSION_TIMEOUT.equals(e.getStatusCode().value()))
            {
                result.setDescription("会话已经超时，请重新登陆");
            }
            data.setResultInfo(result);
            return data;
        }
        // 统一抛出进行处理
        catch (Exception e)
        {
            throw e;
        }
    }

    /*
     * 前端请求传递的请求头参数封装
     * @param request
     * @param builder
     */
    protected void requestHeadParams(HttpServletRequest request, HttpHeaders headers)
    {
        if (CommonUtil.isNotEmpty(request) && CommonUtil.isNotNull(headers))
        {
            String headerName = null;
            Enumeration<String> headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements())
            {
                headerName = headerNames.nextElement();
                headers.add(headerName, request.getHeader(headerName));
            }
        }
    }

    /*
     * 自定义客户端必传请求头参数
     * @param request
     * @param builder
     */
    protected void customHeadParams(HttpServletRequest request, HttpHeaders headers)
    {
        if (CommonUtil.isNotEmpty(request) && CommonUtil.isNotNull(headers))
        {
            String channelCode = applicationPropertiesUtil.getStrProp("server.intf.channel-code");
            String channelSecret = applicationPropertiesUtil.getStrProp("server.intf.channel-secret");
            Assert.hasText(channelCode, "渠道编码不能为空");
            Assert.hasText(channelSecret, "渠道秘钥不能为空");
            String remoteAddress = request.getRemoteAddr();
            Long timestamp = new Date().getTime();

            StringBuffer sb = new StringBuffer();
            String sign = sb.append(channelCode).append(CommonConstant.CHAR_COLON) // 渠道编码
                    .append(channelSecret).append(CommonConstant.CHAR_COLON) // 渠道秘钥
                    .append(remoteAddress).append(CommonConstant.CHAR_COLON) // 请求放ip地址
                    .append(timestamp) // 请求时间戳
                    .toString();

            Boolean encodeDisabled = applicationPropertiesUtil.getBoolProp("server.encode.disable", true);
            headers.add(CustomSecurityHeader.SECURITY_CHANNEL_CODE, channelCode);
            headers.add(CustomSecurityHeader.SECURITY_REQUEST_IP, remoteAddress);
            headers.add(CustomSecurityHeader.SECURITY_CHANNEL_SIGN, encodeDisabled ? sign : MD5CoderUtil.encode(sign)); // 使用MD5加密
            headers.add(CustomSecurityHeader.SECURITY_CHANNEL_TIMESTAMP, String.valueOf(timestamp));

            // 将请求中的token参数设置到请求头中
            headers.add(CustomSecurityHeader.SECURITY_REQUEST_TOKEN, request.getHeader("token"));
        }

    }
}
