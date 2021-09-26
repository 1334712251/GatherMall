package com.gathermall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients(basePackages = "com.gathermall.ware.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class GatherWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherWareApplication.class,args);
    }
}
