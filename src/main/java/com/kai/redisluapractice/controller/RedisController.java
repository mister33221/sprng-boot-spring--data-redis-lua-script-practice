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
    @Operation(summary = "Increase value of key", description = "Increase value of key", tags = { "lua script" })
    public String increase(@PathVariable String key) {
        // Load lua script
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // Set lua script location
        redisScript.setLocation(new ClassPathResource("/lua/increase.lua"));
        // Set lua script return type
        redisScript.setResultType(Long.class);

        // Execute lua script
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key));
        return "Value of " + key + " after increase: " + result;
    }

}
