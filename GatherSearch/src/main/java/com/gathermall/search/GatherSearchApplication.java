package com.gathermall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatherSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherSearchApplication.class, args);
    }
}
