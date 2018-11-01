package com.yinhetianze.configurations.components;

import com.yinhetianze.core.utils.ApplicationPropertiesUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.security.custom.CustomACLFilter;
import com.yinhetianze.security.custom.CustomAccessLimitFilter;
import com.yinhetianze.security.custom.CustomChannelCheckFilter;
import com.yinhetianze.security.custom.CustomTokenCheckFilter;
import com.yinhetianze.security.custom.userdetails.impl.RedisUserDetailsService;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by Administrator
 * on 2018/1/30.
 */
@Configuration
// core包中的内置实现，基础框架使用时可以使用，也可以自己实现接口定制
@MapperScan(basePackages = {
        "com.yinhetianze.common.business",
        "com.yinhetianze.business",
        "com.yinhetianze.systemservice",
        "com.yinhetianze.security",
        "com.yinhetianze.listener"
})
@ComponentScan(basePackages = {
        "com.yinhetianze.common.business",
        "com.yinhetianze.business",
        "com.yinhetianze.configurations.components",
        "com.yinhetianze.core.business",
        "com.yinhetianze.security"
})
@ServletComponentScan(basePackages = {"com.yinhetianze.web", "com.yinhetianze.security"})
public class ComponentsConfig
{

    /**
     * 自定义redis管理工具类注册
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisManager redisManager(RedisTemplate redisTemplate)
    {
        RedisManager rm = new RedisManager();
        rm.setRedisTemplate(redisTemplate);
        return rm;
    }

    /**
     *
     * @param redisManager
     * @return
     */
    @Bean("redisUserDetails")
    public RedisUserDetailsService userDetailsServiceImpl(RedisManager redisManager)
    {
        RedisUserDetailsService userDetailsService = new RedisUserDetailsService(redisManager);
        return userDetailsService;
    }

    @Bean("applicationPropertiesUtil")
    public ApplicationPropertiesUtil getApplicationPropertiesUtil(){
        return new ApplicationPropertiesUtil();
    }


    @Bean
    public CustomACLFilter customACLFilter()
    {
        CustomACLFilter filter = new CustomACLFilter();
        return filter;
    }

    @Bean
    public CustomChannelCheckFilter customChannelCheckFilter()
    {
        CustomChannelCheckFilter filter = new CustomChannelCheckFilter();
        return filter;
    }

    @Bean
    public CustomTokenCheckFilter customTokenCheckFilter()
    {
        CustomTokenCheckFilter filter = new CustomTokenCheckFilter();
        return filter;
    }

    @Bean
    public CustomAccessLimitFilter customAccessLimitFilter()
    {
        CustomAccessLimitFilter filter = new CustomAccessLimitFilter();
        return filter;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("PUT", "DELETE","GET","POST","OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("access-control-allow-headers",
                                "access-control-allow-methods",
                                "access-control-allow-origin",
                                "access-control-max-age",
                                "X-Frame-Options")
                        .allowCredentials(false).maxAge(3600);
            }
        };

    }
}

