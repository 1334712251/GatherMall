package com.gathermall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.gathermall.order.dao")
@SpringBootApplication
public class GatherOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherOrderApplication.class,args);
    }

}
