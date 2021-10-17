package com.gathermall.product.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;


@EnableConfigurationProperties(CacheProperties.class)
@Configuration
@EnableCaching
public class MyCacheConfig {

    // @Autowired
    // public CacheProperties cacheProperties;

    /**
     * 配置文件的配置没有用上
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // config = config.entryTtl();
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        //将配置文件中所有的配置都生效
        //指定ttl
        if (redisProperties.getTimeToLive() != null) {
            Duration timeToLive = redisProperties.getTimeToLive();
//            long l = ThreadLocalRandom.current().nextLong(300000);   //5*1000*60
//            Duration randomTime = Duration.ofMillis(l);//毫秒
//            config = config.entryTtl(redisProperties.getTimeToLive().plus(randomTime));
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        //指定前缀方法已过期
//        if (redisProperties.getKeyPrefix() != null) {
//            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
//        }
        //指定前缀
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());  //prefixCacheNameWith
        }
        //是否缓存空值
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        //是否开启前缀功能
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        return config;
    }

}
