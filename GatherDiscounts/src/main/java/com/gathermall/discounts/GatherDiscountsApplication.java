package com.gathermall.discounts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.gathermall.discounts.dao")
@SpringBootApplication
public class GatherDiscountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherDiscountsApplication.class,args);
    }

}