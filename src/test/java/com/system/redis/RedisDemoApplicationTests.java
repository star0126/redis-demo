package com.system.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;


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
     * @create: 2020/1/16 22:02
     * @description: redis切换databases和清除数据
     */
    @Test
    void redisSelectAndFlush(){
        //切换DB  切换至0号DB
        String select = jedis.select(0);
        System.out.println(select);
        //删除当前所在DB数据
        String flushDB = jedis.flushDB();
        System.out.println(flushDB);
        //删除全部DB数据
        String flushAll = jedis.flushAll();
        System.out.println(flushAll);
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/16 22:07
     * @description: redis之String数据类型的操作
     */
    @Test
    void redisString(){
        jedis.select(1);
        jedis.set("k1", "1");
        String k1 = jedis.get("k1");
        System.out.println(k1);
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/16 22:23
     * @description: redis事务--执行事务
     */
    @Test
    void redisMulti(){
        Transaction transaction = jedis.multi();
        transaction.set("k2","v2");
        transaction.set("k3","v3");
        transaction.set("k4","v4");
        transaction.exec();
        System.out.println(jedis.get("k4"));
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/16 22:36
     * @description: redis事务--放弃事务执行
     */
    @Test
    void redisMulti2(){
        Transaction transaction = jedis.multi();
        transaction.set("k5","v5");
        transaction.set("k6","v6");
        transaction.discard();
        System.out.println(jedis.get("k6"));
    }



}
