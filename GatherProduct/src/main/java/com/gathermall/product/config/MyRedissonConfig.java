package com.gathermall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MyRedissonConfig {

    /**
     * 单节点模式
     *
     * 所有对Redisson的使用都是通过RedissonClient
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://172.26.218.232:6379").setPassword("123456");

        //2、根据Config创建出RedissonClient实例
        //Redis url should start with redis:// or rediss://
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


//    /**
//     * 多节点模式
//     *
//     * @return
//     * @throws IOException
//     */
//    @Bean(destroyMethod="shutdown")
//    public RedissonClient redisson() throws IOException {
//        Config config = new Config();
//        config.useClusterServers().addNodeAddress("120.26.84.226:6379","120.26.84.227:6379","120.26.84.228:6379").setPassword("123456");
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }

}
