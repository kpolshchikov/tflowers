package ru.tbank.tflowers.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
                .withCacheConfiguration(BOUQUET_CACHE,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1))
                                .serializeKeysWith(
                                        RedisSerializationContext.SerializationPair.fromSerializer(
                                                new StringRedisSerializer()))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                                        getJsonSerializer())))
                .build();
    }

    private GenericJackson2JsonRedisSerializer getJsonSerializer() {
        return new GenericJackson2JsonRedisSerializer(getObjectMapper());
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );
        return objectMapper;
    }
}
