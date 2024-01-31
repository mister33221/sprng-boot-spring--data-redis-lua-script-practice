package com.kai.redisluapractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class RedisController {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @PostMapping("/set/{key}/{value}")
    @Operation(summary = "Set value of key", description = "Set value of key", tags = { "create/update" })
    public String set(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Value of " + key + " after set: " + redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/get/{key}")
    @Operation(summary = "Get value of key", description = "Get value of key", tags = { "read" })
    public String get(@PathVariable String key) {
        return "Value of " + key + ": " + redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all keys and values", description = "Get all keys and values", tags = { "read" })
    public String getAll() {
        return redisTemplate.opsForValue().multiGet(redisTemplate.keys("*")).toString();
    }

    @DeleteMapping("/delete/{key}")
    @Operation(summary = "Delete key", description = "Delete key", tags = { "delete" })
    public String delete(@PathVariable String key) {
        redisTemplate.delete(key);
        return "Value of " + key + " after delete: " + redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/increase/{key}")
    public String increase(@PathVariable String key) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("/lua/increase.lua"));
        redisScript.setResultType(Long.class);

        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key));
        return "Value of " + key + " after increase: " + result;
    }

}
