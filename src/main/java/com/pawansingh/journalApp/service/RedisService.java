package com.pawansingh.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> weatherResponseClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            // this will return whatever we want it to return like this time Complete Weather Response
            // but what if we don't know what is getting returned and we want to convert the object
            // returned to a specific data type like Weather Response in our case, use object mapper for this

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(o.toString(),weatherResponseClass);
        } catch (Exception e) {
            log.error("Exception" + e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl){ // ttl -> time to live
//        redisTemplate.opsForValue().set(key,o,ttl); // without time unit
        redisTemplate.opsForValue().set(key,o,ttl, TimeUnit.HOURS);
    }

}
