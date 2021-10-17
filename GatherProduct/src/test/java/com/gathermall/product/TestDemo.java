package com.gathermall.product;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@EnableConfigurationProperties(CacheProperties.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

    @Autowired
    private RedissonClient redissonClient;


    @Test
    public void redisson(){
        System.out.println(redissonClient);
    }


    @Test
    public void ss(){

//        Instant first = Instant.now();
//        // wait some time while something happens
//        Instant second = Instant.now();
//        Duration duration = Duration.between(first, second);
        long l = ThreadLocalRandom.current().nextLong(300000);   //5*1000*60
        System.out.println(l);
        Duration durationMillis = Duration.ofMillis(3600000);//毫秒
        Duration ss = Duration.ofMillis(l);//毫秒
        System.out.println(durationMillis.plus(ss));
    }

}
