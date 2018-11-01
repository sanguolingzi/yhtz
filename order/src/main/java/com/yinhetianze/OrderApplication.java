package com.yinhetianze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableCaching
@SpringBootApplication
public class OrderApplication extends SpringBootServletInitializer { // 启动war部署
	// 指定war部署加载的配置文件
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(OrderApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
