package com.lunar.lunarMgmt.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableJpaRepositories
@RequiredArgsConstructor
public class RedisRepositoryConfig {

    // redis의 옵션을 설정을 할 수 있게 해준다.
    private final RedisProperties redisProperties;

    // lettuce
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());// localhost:6379로 기본 설정이 되어 있음.
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {// redisTemplate은 redis 서버에 redis 커맨드를 수행하기 위한
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());// 위의 connection 정보 setting
        redisTemplate.setKeySerializer(new StringRedisSerializer());// key 직렬화 설정 안하면 깨져서 데이터가 들어감
        redisTemplate.setValueSerializer(new StringRedisSerializer());// value 직렬화 설정 안하면 깨져서 데이터가 들어감
        return redisTemplate;
    }
}
