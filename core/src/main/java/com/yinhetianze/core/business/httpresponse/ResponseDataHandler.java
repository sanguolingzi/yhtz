package com.yinhetianze.core.business.httpresponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理请求响应结果的统一方式
 * 自定义实现
 * @param <T>
 */
public interface ResponseDataHandler<T>
{
//    ResponseData<T> handleResponseData(HttpServletRequest request, HttpServletResponse response, Object...params);

    ResponseData<T> handleResponseData(String url, HttpEntity entity, HttpMethod method);
}
