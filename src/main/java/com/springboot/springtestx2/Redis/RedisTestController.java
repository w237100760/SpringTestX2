package com.springboot.springtestx2.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Redis")
public class RedisTestController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    public void test(){
        List<String> list = new ArrayList<>();
        list.add("kkk");
//        redisService.setCacheList("test:key:",list);
//        System.out.println("redis ..."+ redisService.getCacheList("test:key:") );
        redisTemplate.opsForList();
        redisTemplate.opsForHash();
        redisTemplate.opsForValue();
        redisTemplate.opsForSet();
        redisTemplate.opsForZSet();// sorted set
    }
}
