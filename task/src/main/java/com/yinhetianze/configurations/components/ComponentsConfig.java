package com.yinhetianze.configurations.components;

import com.yinhetianze.core.task.InitModuleHandler;
import com.yinhetianze.core.task.InitModuleManager;
import com.yinhetianze.core.utils.ApplicationPropertiesUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.security.custom.userdetails.impl.RedisUserDetailsService;
import com.yinhetianze.web.servlet.InitServlet;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by Administrator
 * on 2018/1/30.
 */
@Configuration
@MapperScan(basePackages = {
        "com.yinhetianze.common.business",
        "com.yinhetianze.business",
        "com.yinhetianze.security",
        "com.yinhetianze.systemservice"
})
@ComponentScan(basePackages = {
        "com.yinhetianze.common.business",
        "com.yinhetianze.business",
        "com.yinhetianze.security",
        "com.yinhetianze.systemservice"
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
    public RedisManager getRedisManager(RedisTemplate redisTemplate) {
        RedisManager rm = new RedisManager();
        rm.setRedisTemplate(redisTemplate);
        return rm;
    }

    @Bean
    public InitModuleManager initModuleManager(InitModuleHandler...initModuleHandler)
    {
        InitModuleManager manager = new InitModuleManager(initModuleHandler);
        return manager;
    }

    //@Bean
    public InitServlet initServlet()
    {
        return new InitServlet();
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

}
