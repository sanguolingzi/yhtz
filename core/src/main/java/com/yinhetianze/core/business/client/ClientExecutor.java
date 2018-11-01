package com.yinhetianze.core.business.client;

import com.yinhetianze.core.business.httpresponse.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户端接口调用执行器
 * @param <T>
 */
public interface ClientExecutor<T>
{
    /**
     * 子类实现的具体接口调用方法
     * @param request
     * @param response
     * @param pathParam
     * @return
     */
    ResponseData<T> execute(HttpServletRequest request, HttpServletResponse response, String pathParam);

    /**
     * 调用方进行熔断操作
     * @param request
     * @param response
     * @return
     */
    ResponseData<T> fusing(HttpServletRequest request, HttpServletResponse response, String pathParam);
}
