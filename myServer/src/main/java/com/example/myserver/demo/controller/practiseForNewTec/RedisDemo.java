package com.example.myserver.demo.controller.practiseForNewTec;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 需要RedisConfig.java
 */
@Controller
@RequestMapping("/redisTest")
public class RedisDemo {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //添加
    @RequestMapping(value = "/redisAdd")
    @ResponseBody
    public void saveRedis() {
        stringRedisTemplate.opsForValue().set("a", "test");
    }

    //获取
    @RequestMapping(value = "/redisGet")
    @ResponseBody
    public String getRedis() {
        return stringRedisTemplate.opsForValue().get("a");
    }

}
