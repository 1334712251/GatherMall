package com.gathermall.operation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.gathermall.operation.dao")
@SpringBootApplication
public class GatherOperationApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherOperationApplication.class,args);
    }

}