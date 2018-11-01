package com.yinhetianze.security.custom;

import com.yinhetianze.core.utils.*;
import com.yinhetianze.security.custom.util.CustomSecurityHeader;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 接口调用单位时间内的次数控制
 * 默认1(配置)分钟内限制相同用户访问相同接口100(配置)次，超过100次后锁定
 */
@Order(1)
public class CustomAccessLimitFilter extends CustomAbstractSecurityFilter
{
    /**
     * 单位时间内可访问的次数
     */
    //@Value("${security.access.limit.hits:100}")
    private Integer hits;

    /**
     * 统计时限周期
     * 单位分钟
     */
    //@Value("${security.access.limit.period:1}")
    private Integer period;

    /**
     * 是否禁用过滤器标志
     */
    //@Value("${security.access.limit.disabled:}")
    private Boolean disabled;

    private Set<String> anonymousUriSet;

    @Autowired
    private ApplicationPropertiesUtil applicationPropertiesUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        super.init(filterConfig);
        String disabled = applicationPropertiesUtil.getStrProp("security.access.limit.disabled","");
        // 没有指定当前过滤器的属性则使用公共父类设置
        if (CommonUtil.isEmpty(disabled))
        {
            this.disabled = super.disabled;
        }else{
            this.disabled = Boolean.parseBoolean(disabled);
        }


        this.period = applicationPropertiesUtil.getIntProp("security.access.limit.period",1);

        this.hits = applicationPropertiesUtil.getIntProp("security.access.limit.hits",10);

        this.anonymousUriSet = initAnonymousUris(super.anonymousUris);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if(req.getMethod().equals(HttpMethod.OPTIONS.name())){
            chain.doFilter(request, response);
            return;
        }

        // 不适用过滤器
        if (CommonUtil.isEmpty(disabled) || disabled)
        {
            chain.doFilter(request, response);
            return;
        }

//        System.err.println(req.getSession().getId()); // 5126CB1A9B84F6AB72E2BE753E7190A9
        // 不需要过滤的地址
        if (isAnonymousUri(req, anonymousUriSet))
        {
            chain.doFilter(request, resp);
            return;
        }

        // 参数项
        StringBuffer sb = new StringBuffer();
        String token = req.getHeader(CustomSecurityHeader.SECURITY_REQUEST_TOKEN);
        String ipAddress = req.getHeader(CustomSecurityHeader.SECURITY_REQUEST_IP);
        String sessionId = req.getHeader(CustomSecurityHeader.SECURITY_REQUEST_SESSION_ID);
        String requestUri = req.getRequestURI();
        RedisManager redisManager = (RedisManager) ApplicationContextFactory.getBean("redisManager");

        Object obj = null;
        String key = "limit:";
        if (CommonUtil.isNotEmpty(token))
        {
            sb.append(key).append(token).append(CommonConstant.CHAR_COLON).append(requestUri);
            key = sb.toString();
            obj = redisManager.getValue(key);
        }
        else
        {
            sb.append(key).append(ipAddress).append(CommonConstant.CHAR_COLON)
                    .append(sessionId).append(CommonConstant.CHAR_COLON).append(requestUri);
            key = sb.toString();
            obj = redisManager.getValue(key);
        }

        // 接口访问限制，1分钟内相同接口只能访问100次 : 100为配置项
        if ("Forbidden".equals(obj))
        {
            sendErrorResponse(resp, HttpStatus.SC_FORBIDDEN);
            return;
        }

        try
        {
            // 转换字符串为整数
            if (CommonUtil.isNotEmpty(obj))
            {
                obj = Integer.parseInt(obj + "");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(CustomAccessLimitFilter.class, e);
        }

        // 在redis中设置该值
        if (obj instanceof Integer)
        {
            // 超过限制次数，禁止访问
            int count = (int) obj;
            if (count > hits)
            {
                // 将当前访问记录为禁止访问状态，10分钟时间
                redisManager.setValue(key, "Forbidden", 10 * 60 * 1000l);

                LoggerUtil.warn(CustomAccessLimitFilter.class, "接口调用已经超过限制值，访问key:[{}]，访问次数：[{}]", new Object[]{key, count});
                sendErrorResponse(resp, HttpStatus.SC_FORBIDDEN, "访问被禁止");
                return;
            }

            // 访问次数+1
            redisManager.setValue(key, String.valueOf(count + 1), period * 60 * 1000l);
        }
        else
        {
            // 从1开始累计访问次数
            redisManager.setValue(key, "1", period * 60 * 1000l);
        }

        chain.doFilter(request, resp);
    }
}
