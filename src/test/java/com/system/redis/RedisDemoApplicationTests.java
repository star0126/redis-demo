package com.system.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


@SpringBootTest
class RedisDemoApplicationTests {

    private Jedis jedis = new Jedis("127.0.0.1",6379);

    /**
     * @author: guoqing-li
     * @create: 2020/1/16 21:14
     * @description: redis联通测试
     */
    @Test
    void redisLink() {
        String ping = jedis.ping();
        if ("PONG".equalsIgnoreCase(ping)){
            System.out.println("redis联通成功！");
        }else {
            System.out.println("redis联通不成功！");
        }
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 10:52
     * @description: 获取当前redis数据库中的key数量和key的K-V值
     */
    @Test
    void redisSundry(){
        //切换为0数据库
        String select = jedis.select(0);
        System.out.println("ok".equalsIgnoreCase(select)?"切换redis数据库成功！":"切换redis数据库不成功！");
        Long dbSize = jedis.dbSize();
        System.out.println("当前redis数据库key数量："+dbSize);
        System.out.println("-------------------当前数据库的keys值-----------------------");
        Set<String> keys = jedis.keys("*");
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            System.out.println("——————————————");
            System.out.println("key="+key);
            System.out.println("value="+jedis.get(key));
        }
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 10:56
     * @description: 删除redis数据库数据
     */
    @Test
    void redisFlush(){
        jedis.select(0);
        String flushDB = jedis.flushDB();
        System.out.println("ok".equalsIgnoreCase(flushDB)?"删除当前redis数据库数据成功！":"删除当前redis数据库数据不成功！");
        String flushAll = jedis.flushAll();
        System.out.println("OK".equalsIgnoreCase(flushAll)?"删除全部redis数据库数据成功！":"删除全部redis数据库数据不成功！");
    }

}
