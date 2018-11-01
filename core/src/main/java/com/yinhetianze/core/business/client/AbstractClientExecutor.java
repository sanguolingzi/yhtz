package com.yinhetianze.core.business.client;

import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.*;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * springcloud客户端调用抽象类
 * 需要子类实现客户端负载均衡与升降级返回熔断机制
 * @param <T>
 */
public abstract class AbstractClientExecutor<T> implements ClientExecutor<T>
{
    @Autowired
    protected ApplicationPropertiesUtil applicationPropertiesUtil;

    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public final ResponseData<T> execute(HttpServletRequest request, HttpServletResponse response, String pathParam)
    {
        try
        {
            // 获取服务server
            String serverUrl = getServerUrl(request, pathParam);
            LoggerUtil.info(AbstractClientExecutor.class, "Access server url is : [{}]", new Object[]{serverUrl});

            // 拼凑请求参数
            HttpEntity<?> entity = handleRequestEntity(request, serverUrl);

            // 调用接口
            return executeInvoke(serverUrl, entity);
        }
        catch (RestClientException e)
        {
            LoggerUtil.error(AbstractClientExecutor.class, e);

            // 所有解析响应失败的结果全部作为登陆超时处理
            ResponseData<T> responseData = new ResponseData<>();
            // 自定义SC_SESSION_TIMEOUT结果码，表示session超时
            Result result = new Result(String.valueOf(ResponseConstant.SC_SESSION_TIMEOUT), "会话超时，请重新登陆");
            responseData.setResultInfo(result);
            return responseData;
        }
        catch (Exception e)
        {
            LoggerUtil.error(AbstractClientExecutor.class, e);

            ResponseData<T> responseData = new ResponseData<>();
            Result result = new Result("系统繁忙,请稍后再试");
            responseData.setResultInfo(result);
            return responseData;
        }
    }

    /**
     * 获取请求url
     * 对应服务的服务名称
     * @param request
     * @param pathParam
     * @return
     */
    protected String getServerUrl(HttpServletRequest request, String pathParam)
    {
        Asserts.notEmpty(request, "[request] can not be null......");
//        Asserts.notEmpty(pathParam, "[pathParam] can not be null......");

        // 服务名称
        String serverName = getServerName();
        Asserts.notEmpty(serverName, "Target server name can not be null......");

        StringBuffer sb = new StringBuffer();

        // 请求协议
        if (applicationPropertiesUtil.getBoolProp("server.ssl.enabled", false))
        {
            sb.append(CommonConstant.PROTOCOL_HTTPS);
        }
        else
        {
            sb.append(CommonConstant.PROTOCOL_HTTP);
        }
        // 服务名称
        sb.append(serverName);
        // 服务上下文
        sb.append(getServerContext());
        if (CommonUtil.isNotNull(pathParam))
        {
            // 服务uri 接/
            sb.append(CommonConstant.CHAR_SLASH);
            sb.append(pathParam.replaceAll("--", "/"));
        }

        // uri参数占位, http://ip:port/path?param1={param1}&param2={param2}......
        return handleUriParamsUrl(request, sb);

//        return sb.toString();
    }

    /**
     * 接口调用实现方式由子类进行实现
     * @param serverUrl
     * @param entity
     * @param uriParams
     * @return
     */
    public abstract ResponseData<T> executeInvoke(String serverUrl, HttpEntity<?> entity, Object...uriParams);

    /**
     * 处理请求参数
     * @param request
     * @return
     */
    public abstract HttpEntity<?> handleRequestEntity(HttpServletRequest request, String uri) throws Exception;

    /**
     * 子类实现调用的服务名称
     * @return
     */
    protected abstract String getServerName();

    /**
     * 默认没有uri参数
     * 则不需要进行uri重构
     * 否则由子类进行重写此方法重构
     * @param request
     * @param sb
     */
    protected String handleUriParamsUrl(HttpServletRequest request, StringBuffer sb)
    {
        return sb.toString();
    }

    /**
     * 获取服务上下文，默认为/
     * @return
     */
    protected String getServerContext()
    {
        return CommonConstant.CHAR_SLASH;
    }

    /**
     * 处理响应结果
     * @param respEntity
     * @return
     */
    protected ResponseData handleResponseData(ResponseEntity<ResponseData> respEntity)
    {
        if (CommonUtil.isEmpty(respEntity))
        {
            return null;
        }

        if (HttpStatus.SC_OK == respEntity.getStatusCodeValue())
        {
            return respEntity.getBody();
        }
        else
        {
            ResponseData data = new ResponseData();
            Result result = new Result(String.valueOf(respEntity.getStatusCodeValue()),
                    respEntity.getStatusCode().getReasonPhrase());
            data.setResultInfo(result);
            return data;
        }
    }
}
