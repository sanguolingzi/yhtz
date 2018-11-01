package com.yinhetianze.kernel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.encryption.SingleTextEncryptorLocator;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 配置中心
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class KernelApplication extends SpringBootServletInitializer
{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(KernelApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(KernelApplication.class, args);
	}
}
