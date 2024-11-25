package com.example.Short_url.service.impl;

import com.example.Short_url.service.UrlShorteningRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UrlShorteningRedisImpl implements UrlShorteningRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveShortUrl(String shortKey, String longUrl){
        stringRedisTemplate.opsForValue().set(shortKey,longUrl);
        stringRedisTemplate.expire(shortKey, 15, TimeUnit.SECONDS);
    }

    @Override
    public String getLongUrl(String shortKey){
        return stringRedisTemplate.opsForValue().get(shortKey);
    }

}
