package com.neverend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieServiceConsumerApplication {
	/**
	 * @LoadBalanced 在使用添加ribbon依赖用于负载均衡调用服务添加
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate template(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(MovieServiceConsumerApplication.class, args);
	}
}
