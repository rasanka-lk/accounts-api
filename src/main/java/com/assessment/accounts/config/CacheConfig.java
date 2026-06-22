package com.assessment.accounts.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
public class CacheConfig {

    public static final String SAVINGS_ACCOUNTS_CACHE = "savingsAccounts";
    public static final String SAVINGS_ACCOUNTS_BY_NUMBER_CACHE = "savingsAccountsByNumber";

    @Bean
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
    RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(RedisSerializer.java())
                );
    }

    @Bean
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
    CacheManager redisCacheManager(
            RedisConnectionFactory redisConnectionFactory,
            RedisCacheConfiguration redisCacheConfiguration
    ) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    CacheManager fallbackCacheManager() {
        return new ConcurrentMapCacheManager(
                SAVINGS_ACCOUNTS_CACHE,
                SAVINGS_ACCOUNTS_BY_NUMBER_CACHE
        );
    }
}
