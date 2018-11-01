package com.yinhetianze.configurations.components;

import com.yinhetianze.core.utils.ApplicationPropertiesUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.security.custom.CustomACLFilter;
import com.yinhetianze.security.custom.CustomAccessLimitFilter;
import com.yinhetianze.security.custom.CustomChannelCheckFilter;
import com.yinhetianze.security.custom.CustomTokenCheckFilter;
import com.yinhetianze.security.custom.userdetails.impl.RedisUserDetailsService;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.RedeliveryPolicy;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.jms.core.JmsTemplate;
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
        "com.yinhetianze.security",
        "com.yinhetianze.listener",
        "com.yinhetianze.systemservice"})
@ComponentScan(basePackages = {"com.yinhetianze.common.business","com.yinhetianze.security"})
@ServletComponentScan("com.yinhetianze.web")
public class ComponentsConfig
{

    /**
     * 自定义redis管理工具类注册
     * @param redisTemplate
     * @return
     */
    @Bean("redisManager")
    public RedisManager getRedisManager(RedisTemplate redisTemplate)
    {
        RedisManager rm = new RedisManager();
        rm.setRedisTemplate(redisTemplate);
        return rm;
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


/*
    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy  redeliveryPolicy=   new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(2);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(5000);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory (@Value("${spring.activemq.broker-url}")String url, RedeliveryPolicy redeliveryPolicy){
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(
                        "admin",
                        "admin",
                        url);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean("customerJmsTemplate")
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory){
        JmsTemplate jmsTemplate=new JmsTemplate();
        jmsTemplate.setDeliveryMode(1);//进行持久化配置 1表示非持久化，2表示持久化</span>
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        //jmsTemplate.setDefaultDestination(queue); //此处可不设置默认，在发送消息时也可设置队列
        jmsTemplate.setSessionAcknowledgeMode(2);//客户端签收模式</span>
        jmsTemplate.setSessionTransacted(false);
        jmsTemplate.setSessionAcknowledgeModeName("CLIENT_ACKNOWLEDGE");
        return jmsTemplate;
    }
*/
}
