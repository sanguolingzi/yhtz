package com.yinhetianze.client.http.servlet;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.ResponseDataHandler;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.Asserts;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 客户端响应处理器
 * 客户端接口调用实现，集成熔断机制，提供高效保障
 */
public class ClientResponseDataHandler implements ResponseDataHandler
{
    @Autowired
    private RestTemplate restTemplate;

    /*
     * 封装响应体
     * 针对发生网络请求错误或者响应结果异常时提供公共封装方法
     * @param code
     * @param description
     * @return
     */
    private ResponseData responseData(String code, String description)
    {
        ResponseData data = new ResponseData();
        // 客户端自定义响应编码9997
        data.setResultInfo(new Result(CommonUtil.isNotEmpty(code) ? code : "9997",
                CommonUtil.isNotEmpty(description) ? description : "系统繁忙，请稍后再试"));
        return data;
    }

    /**
     * 熔断保障方法
     * @param url
     * @param entity
     * @param method
     * @return
     */
    public ResponseData hystrixCallback(String url, HttpEntity entity, HttpMethod method)
    {
        LoggerUtil.info(ClientResponseDataHandler.class, "系统访问熔断触发：访问地址[{}]，请求方法[{}]，请求体[{}]", new Object[]{url, method, entity});
        return responseData(null, null);
    }

    /**
     * 接口调用实现
     * @param url
     * @param entity
     * @param method
     * @return
     */
    @HystrixCommand(fallbackMethod = "hystrixCallback")
    @Override
    public ResponseData handleResponseData(String url, HttpEntity entity, HttpMethod method)
    {
        Asserts.notBlank(url, "请求地址参数[url]不能为空");
        Asserts.notEmpty(method, "请求方法参数[method]不能为空");

        try
        {
            ResponseEntity<ResponseData> respEntity = restTemplate.exchange(url, method, entity, ResponseData.class);
            if (HttpStatus.SC_OK == respEntity.getStatusCodeValue())
            {
                return respEntity.getBody();
            }
            else
            {
                return responseData(String.valueOf(respEntity.getStatusCodeValue()), respEntity.getStatusCode().getReasonPhrase());
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(ClientResponseDataHandler.class, e);
            throw e;
        }

    }
}
