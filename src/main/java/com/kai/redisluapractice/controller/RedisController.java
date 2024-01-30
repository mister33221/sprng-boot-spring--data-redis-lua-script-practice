package com.kai.redisluapractice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class RedisController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @GetMapping("/increase/{key}")
    public String increase(@PathVariable String key) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("/lua/increase.lua"));
        redisScript.setResultType(Long.class);

        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key));
        return "Value of " + key + " after increase: " + result;
    }

}
