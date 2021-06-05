package com.gathermall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.gathermall.ware.dao")
@SpringBootApplication
public class GatherWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherWareApplication.class,args);
    }
}
