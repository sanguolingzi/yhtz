package com.yinhetianze.client.configurations;

import com.yinhetianze.client.http.filter.AccessControllAllowedFilter;
import com.yinhetianze.client.http.servlet.ClientServlet;
import com.yinhetianze.core.utils.ApplicationPropertiesUtil;
import com.yinhetianze.core.utils.HttpClientManager;
import com.yinhetianze.core.utils.RedisManager;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * Created by Administrator
 * on 2018/1/30.
 */
@Configuration
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

    @Bean("applicationPropertiesUtil")
    public ApplicationPropertiesUtil getApplicationPropertiesUtil(){
        return new ApplicationPropertiesUtil();
    }




    /*@Bean
    public ClientServlet clientServlet()
    {
        ClientServlet client = new ClientServlet();
        return client;
    }*/

    @Bean
    public HttpClientManager httpClientManager()
    {
        return new HttpClientManager();
    }

    @Bean
    public AccessControllAllowedFilter accessControllAllowedFilter()
    {
        return new AccessControllAllowedFilter();
    }

    @Bean
    @LoadBalanced // 客户端负载均衡
    public RestTemplate restTemplate()
    {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return new RestTemplate();
    }

}
