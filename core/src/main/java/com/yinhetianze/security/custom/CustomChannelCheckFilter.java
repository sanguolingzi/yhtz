package com.yinhetianze.security.custom;

import com.yinhetianze.core.cachedata.CacheData;
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
import java.util.*;

/**
 * 客户端接口调用时渠道信息校验
 * 渠道信息通过sign请求头参数传递
 * sign参数的结构为channelCode:channelSecret:remoteIpAddress:timestamp
 * sign参数通过XXX加密
 */
@Order(1)
public class CustomChannelCheckFilter extends CustomAbstractSecurityFilter
{
    /**
     * 去重设置，内容为anonymousUris分割后的去重集合
     */
    private Set<String> anonymousUriList;

    //@Value("${security.channel.check.anonymous.uris:}")
    private String anonymousUris;

    /**
     * 是否禁用，默认为true
     */
    //@Value("${security.channel.check.disabled:}")
    private Boolean disabled;

    //@Value("${server.encode.disable:true}")
    private Boolean encodeDisabled;

    @Autowired
    private ApplicationPropertiesUtil applicationPropertiesUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        super.init(filterConfig);

        String disabled = applicationPropertiesUtil.getStrProp("security.channel.check.disabled","");
        // 默认从父类设置属性
        if (CommonUtil.isEmpty(disabled))
        {
            this.disabled = super.disabled;
        }else{
            this.disabled = Boolean.parseBoolean(disabled);
        }


        String anonymousUris = applicationPropertiesUtil.getStrProp("security.channel.check.anonymous.uris","");

        if (CommonUtil.isEmpty(anonymousUris))
        {
            this.anonymousUris = super.anonymousUris;
        }else{
            this.anonymousUris = anonymousUris;
        }

        this.encodeDisabled = Boolean.parseBoolean(applicationPropertiesUtil.getStrProp("server.encode.disable","true"));


        this.anonymousUriList = initAnonymousUris(this.anonymousUris);
        if (CommonUtil.isNotEmpty(failedUrl))
        {
            anonymousUriList.add(this.failedUrl);
        }
    }

    @Override
    public void destroy()
    {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

       if(request.getMethod().equals(HttpMethod.OPTIONS.name())){
            filterChain.doFilter(request, response);
            return;
        }

        // 如果不使用当前过滤器  则下一步
        if (CommonUtil.isNull(disabled) || disabled)
        {
            filterChain.doFilter(request, response);
            return;
        }

        // 如果是不需要校验的地址则下一步
        if (isAnonymousUri(request, anonymousUriList))
        {
            filterChain.doFilter(request, response);
            return;
        }

        // 参数不正确，需要返回未授权信息
        String sign = request.getHeader(CustomSecurityHeader.SECURITY_CHANNEL_SIGN);
        if (CommonUtil.isEmpty(sign))
        {
            LoggerUtil.info(CustomChannelCheckFilter.class, "签名参数为空");
            response.sendError(HttpStatus.SC_UNAUTHORIZED, "未授权的访问");
            return ;
        }
        String channelCode = request.getHeader(CustomSecurityHeader.SECURITY_CHANNEL_CODE);
        if (CommonUtil.isEmpty(channelCode))
        {
            LoggerUtil.info(CustomChannelCheckFilter.class, "渠道参数为空");
            response.sendError(HttpStatus.SC_UNAUTHORIZED, "未授权的访问");
            return;
        }

        // 通过channelCode获取渠道信息
//        SysChannelInfoService sysChannelInfoServiceImpl = (SysChannelInfoService) ApplicationContextFactory.getBean("sysChannelInfoServiceImpl");
        CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
        if (CommonUtil.isEmpty(sysChannelCacheData))
        {
            LoggerUtil.info(CustomChannelCheckFilter.class, "没有获取到缓存数据对象，获取不到缓存数据。");
            sendErrorResponse(response, HttpStatus.SC_UNAUTHORIZED, "未授权的访问");
        }

//        Map<String, Object> channelInfo = sysChannelInfoServiceImpl.findChannelInfo(channelCode);
        HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
        if (CommonUtil.isEmpty(channelInfo))
        {
            LoggerUtil.info(CustomChannelCheckFilter.class, "渠道信息为空，请检查渠道配置信息。");
            response.sendError(HttpStatus.SC_UNAUTHORIZED, "未授权的访问");
            return;
        }

        // 将渠道信息通过正确的规则排序后使用AES加密
        String encoderInfo = null;
        try
        {
//            encoderInfo = encodeChannelInfo(request, channelInfo);
            encoderInfo = encodeChannelInfo(request, channelInfo.get(channelCode)); // 根据渠道编码获取渠道信息
        }
        catch (Exception e)
        {
            response.sendError(HttpStatus.SC_UNAUTHORIZED, "未授权的访问");
            return;
        }

        // 将encodeInfo与sign进行对比
        if (!sign.equalsIgnoreCase(encoderInfo))
        {
            response.sendError(HttpStatus.SC_UNAUTHORIZED, "未授权的访问");
            return ;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 将渠道信息加密
     * @param channelInfo
     * @return
     */
    private String encodeChannelInfo(HttpServletRequest request, Map<String, Object> channelInfo)
    {
        // 渠道信息不能为空
        if (CommonUtil.isEmpty(channelInfo))
        {
            LoggerUtil.info(CustomChannelCheckFilter.class, "渠道信息为空");
            throw new IllegalArgumentException("渠道信息不能为空");
        }

        // 需要加密的参数信息
        StringBuffer sb = new StringBuffer();
//        String remoteAddress = request.getRemoteAddr();
        String timestamp = request.getHeader(CustomSecurityHeader.SECURITY_CHANNEL_TIMESTAMP);
        String channelCode = (String) channelInfo.get("channelCode");
        String channelSecret = (String) channelInfo.get("channelSecret");

        // 将加密信息按照[channelCode:channelSecret:remoteAddress:timestamp]的格式加密
        sb.append(channelCode).append(CommonConstant.CHAR_COLON).append(channelSecret);

        // 如果请求头参数的ip地址存在则加入到签名中
        String requestAddress = request.getHeader(CustomSecurityHeader.SECURITY_REQUEST_IP);
        if (CommonUtil.isNotEmpty(requestAddress))
        {
            sb.append(CommonConstant.CHAR_COLON).append(requestAddress);
        }
        // 如果请求头参数的时间戳
        if (CommonUtil.isNotEmpty(timestamp))
        {
            sb.append(CommonConstant.CHAR_COLON).append(timestamp);
        }

        // 返回加密信息
        String encodeChannelInfo = sb.toString();
        if (!encodeDisabled)
        {
            LoggerUtil.info(CustomChannelCheckFilter.class, "渠道信息校验，开启了加密方式，被加密的内容：{}", new Object[]{encodeChannelInfo});
            encodeChannelInfo = MD5CoderUtil.encode(encodeChannelInfo);
            LoggerUtil.info(CustomChannelCheckFilter.class, "渠道信息使用MD5加密，加密后的数据：{}", new Object[]{encodeChannelInfo});
        }
        return encodeChannelInfo;
    }

}
