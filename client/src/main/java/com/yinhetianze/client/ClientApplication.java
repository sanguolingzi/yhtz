package com.yinhetianze.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableAutoConfiguration
@SpringCloudApplication
public class ClientApplication extends SpringBootServletInitializer
{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ClientApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}
