package com.system.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) { SpringApplication.run(RedisDemoApplication.class, args); }

}
