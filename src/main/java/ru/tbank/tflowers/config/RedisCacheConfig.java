package ru.tbank.tflowers.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.Set;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    public static final String BOUQUET_CACHE = "bouquetCache";

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .initialCacheNames(Set.of(BOUQUET_CACHE))
                .withCacheConfiguration(BOUQUET_CACHE, RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofDays(1)))
                .build();
    }
}
