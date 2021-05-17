package com.iplume.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * sponsor starter application.
 *
 * @description: @EnableFeignClients, @EnableCircuitBreaker: 为dashboard监控服务.
 *               @EnableEurekaClient: 为eureka client服务.
 * @author: lingchen
 * @date: 2021/4/19
 */
@EnableFeignClients
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class SponsorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SponsorApplication.class, args);
    }
}
