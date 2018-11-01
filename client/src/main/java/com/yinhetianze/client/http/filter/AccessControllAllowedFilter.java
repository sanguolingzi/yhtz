package com.yinhetianze.client.http.filter;

import com.yinhetianze.core.utils.ApplicationPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class AccessControllAllowedFilter implements Filter
{
    //@Value("${server.Access-Controll-Allowed:false}")
    private Boolean accessControllerAllowed;

    //@Value("${server.Access-Control-Allow-Origin:*}")
    private String accessControlAllowOrigin;

    //@Value("${server.Access-Control-Allow-Methods:POST, GET, DELETE, PUT}")
    private String accessControlAllowMethods;

    //@Value("${server.Access-Control-Max-Age:3628800}")
    private Integer accessControlMaxAge;

    //@Value("${server.Access-Control-Allow-Headers:x-requested-with}")
    private String accessControlAllowHeaders;

    @Autowired
    private ApplicationPropertiesUtil applicationPropertiesUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.accessControllerAllowed = Boolean.parseBoolean(applicationPropertiesUtil.getStrProp("server.Access-Controll-Allowed","false"));

        this.accessControlAllowOrigin = applicationPropertiesUtil.getStrProp("server.Access-Control-Allow-Origin","*");

        this.accessControlAllowMethods = applicationPropertiesUtil.getStrProp("server.Access-Control-Allow-Methods","POST, GET, DELETE, PUT");

        this.accessControlMaxAge = applicationPropertiesUtil.getIntProp("server.Access-Control-Max-Age",3628800);

        this.accessControlAllowHeaders = applicationPropertiesUtil.getStrProp("server.Access-Control-Allow-Headers","x-requested-with");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        if (accessControllerAllowed)
        {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

            // 表明它允许"http://xxx"发起跨域请求
            httpResponse.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
            // 表明在xxx秒内，不需要再发送预检验请求，可以缓存该结果
            httpResponse.setHeader("Access-Control-Max-Age", String.valueOf(accessControlMaxAge));
            // 表明它允许xxx方法的外域请求
            httpResponse.setHeader("Access-Control-Allow-Methods", accessControlAllowMethods);
            // 表明它允许跨域请求包含xxx头
            httpResponse.setHeader("Access-Control-Allow-Headers", accessControlAllowHeaders);
        }

        // 特殊访问
        String requestUri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (requestUri.contains("/favicon.ico") || requestUri.contains("/error"))
        {
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy()
    {

    }
}
