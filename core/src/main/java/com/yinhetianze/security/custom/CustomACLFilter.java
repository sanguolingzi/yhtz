package com.yinhetianze.security.custom;

import com.yinhetianze.cachedata.intfpattern.IntfPatternCacheDataModel;
import com.yinhetianze.cachedata.intfpattern.IntfPatternInfo;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationPropertiesUtil;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.security.custom.userdetails.UserDetails;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
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
 * 通过token获取登陆用户的相关权限
 * 根据权限获取对应可以访问的接口列表
 * 最后根据请求的接口地址匹配是否存在于当前用户的可访问接口列表中
 * 存在则继续访问，不存在则返回403请求被拒绝/禁止状态
 * 所有校验统一作为403不被允许的访问进行处理
 */
@Order(3)
public class CustomACLFilter extends CustomAbstractSecurityFilter
{
    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CacheData<IntfPatternCacheDataModel> intfPatternCacheData;
//    private IntfPatternInfoService intfPatternInfoServiceImpl;

    /**
     * 不需要进行校验就可以访问的地址列表
     * 以逗号隔开
     */
    //@Value("${security.acl.anonymous.uris:}")
    private String anonymousUris;

    /**
     * 去重设置，内容为anonymousUris分割后的去重集合
     */
    private Set<String> anonymousUriSet;


    @Autowired
    private ApplicationPropertiesUtil applicationPropertiesUtil;

    /**
     * 禁用标记，默认禁用
     */
    //@Value("${security.acl.disabled:}")
    private Boolean disabled;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        super.init(filterConfig);

        String anonymousUris = applicationPropertiesUtil.getStrProp("security.acl.anonymous.uris","");
        // 没有指定当前过滤器的属性则使用公共父类设置
        if (CommonUtil.isEmpty(anonymousUris))
        {
            this.anonymousUris = super.anonymousUris;
        }else{
            this.anonymousUris = anonymousUris;
        }

        String disabled = applicationPropertiesUtil.getStrProp("security.acl.disabled","");
        if (CommonUtil.isEmpty(disabled))
        {
            this.disabled = super.disabled;
        }else{
            this.disabled = Boolean.parseBoolean(disabled);
        }

        // 从配置文件中的游客地址列表中获取
        anonymousUriSet = initAnonymousUris(this.anonymousUris);
        if (CommonUtil.isNotEmpty(failedUrl))
        {
            anonymousUriSet.add(this.failedUrl);
        }
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

        // 接口访问地址
        String uri = request.getRequestURI();
        uri = uri.lastIndexOf("/") == uri.length() - 1 ? uri.substring(0, uri.length() - 1) : uri; // 去除结尾的/
        if (isAnonymousUri(request, anonymousUriSet))
        {
            filterChain.doFilter(request, response);
            return;
        }

        // 用户访问令牌，用来换取用户可访问的地址列表
        String token = request.getHeader(CustomSecurityHeader.SECURITY_REQUEST_TOKEN);
        if (CommonUtil.isEmpty(token))
        {
            // 返回禁止访问的结果
            sendErrorResponse(response, HttpStatus.SC_FORBIDDEN, "请求被禁止访问");
            return;
        }
        else
        {
            // 对token进行校验，获取对应的用户信息
            UserDetails userDetails = redisUserDetails.getUserDetails(token);
            if (CommonUtil.isEmpty(userDetails))
            {
                // 返回禁止访问的结果
                sendErrorResponse(response, HttpStatus.SC_FORBIDDEN, "请求被禁止访问");
                return;
            }
            else
            {
                // 获取权限集合
//                Collection<String> authorizes = userDetails.getAuthorizes();
                Collection<String> authorizes = new ArrayList<>();
                authorizes.add("USER");
                if (!checkAuthorizedRes(authorizes, uri))
                {
                    sendErrorResponse(response, HttpStatus.SC_FORBIDDEN, "请求被禁止访问");
                    return;
                }

                filterChain.doFilter(request, response);
            }
        }
    }

    private Boolean checkAuthorizedRes(Collection<String> authorized, String uri)
    {
        // 获取所有的权限匹配关系
        IntfPatternCacheDataModel intfPatternModel = intfPatternCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(intfPatternModel))
        {
            // 所有配置的需要特殊权限才能访问的列表
            List<IntfPatternInfo> intfPatterns = intfPatternModel.getIntfPatternList();
            if (CommonUtil.isNotEmpty(intfPatterns))
            {
                String permission = null;
                // 遍历特殊权限访问列表acl
                for (IntfPatternInfo info : intfPatterns)
                {
                    if (CommonUtil.pathMatch(info.getPattern(), uri))
                    {
                        permission = info.getPermission();
                        break;
                    }
                }

                // 判断用户的权限列表中是否包含对应的权限
                if (CommonUtil.isNotEmpty(permission) && CommonUtil.isNotEmpty(authorized))
                {
                    return authorized.contains(permission.trim());
                }
            }
        }
        return false;
    }

}
