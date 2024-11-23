package ru.tbank.tflowers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.tbank.tflowers.bouquet.db.BouquetEntity;

import java.time.Duration;
import java.util.List;
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

    private RedisSerializer<List<BouquetEntity>> getJsonSerializer() {
        ObjectMapper objectMapper = getObjectMapper();
        return new Jackson2JsonRedisSerializer<>(objectMapper, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, BouquetEntity.class));
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
