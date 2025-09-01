package com.pawansingh.journalApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void SendMail(){
        redisTemplate.opsForValue().set("email","developerpawansingh108@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
    }

}
