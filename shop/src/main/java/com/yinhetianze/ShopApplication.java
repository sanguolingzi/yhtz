package com.yinhetianze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ShopApplication extends SpringBootServletInitializer
{
	// 指定war部署加载的配置文件
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(ShopApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
}
