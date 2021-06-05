package com.gathermall.Member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.gathermall.Member.feign")
@EnableDiscoveryClient
@MapperScan("com.gathermall.member.dao")
@SpringBootApplication
public class GatherMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatherMemberApplication.class,args);
    }

}
