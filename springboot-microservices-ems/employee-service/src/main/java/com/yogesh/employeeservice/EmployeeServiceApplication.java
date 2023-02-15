package com.yogesh.employeeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients //feign clients automatically does load balancing too
@EnableEurekaClient
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

/*	@Bean
	@LoadBalanced //use this annotation, if using rest template -- @LoadBalanced internally uses ribbon load balancer
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
*/

/*  @Bean
	public WebClient webClient(){
		return WebClient.builder().build();
	}
*/
}
