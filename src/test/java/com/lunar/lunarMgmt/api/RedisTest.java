package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.common.config.RedisRepositoryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LunarMgmtApplication.class)
public class RedisTest {
    @Autowired
    private RedisRepositoryConfig redisRepositoryConfig;

    @Test
    public void redisSetKeyValueTest(){
        // given
        String key ="keyTest";
        String value="valueTest";

        //when
        ValueOperations<String, Object> valueOperations = redisRepositoryConfig.redisTemplate().opsForValue();
        valueOperations.set(key, value);

        // then
        assertAll(
                () -> assertTrue(valueOperations.get(key).equals(value))
        );
    }
}
