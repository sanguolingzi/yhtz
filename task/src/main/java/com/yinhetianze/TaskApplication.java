package com.yinhetianze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableEurekaClient
@EnableWebMvc
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableCaching
@SpringBootApplication
public class TaskApplication extends SpringBootServletInitializer
{

	// 指定war部署加载的配置文件
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(TaskApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}
}
