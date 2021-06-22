package com.gathermall.product;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.net.HttpURLConnection;

@EnableDiscoveryClient
@MapperScan("com.gathermall.product.dao")
@SpringBootApplication
public class GatherProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherProductApplication.class,args);
    }

}
