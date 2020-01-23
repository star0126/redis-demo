package com.system.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


@SpringBootTest
class RedisDemoApplicationTestsString {

    private Jedis jedis = new Jedis("127.0.0.1",6379);



    /**
     * @author: guoqing-li
     * @date: 2020/1/17 11:18
     * @description: redis的String类型操作 01  简单的存取改删
     */
    @Test
    void redisString01(){
        jedis.select(1);
        jedis.set("k1","v1");
        String k1 = jedis.get("k1");
        System.out.println("k1="+k1);
        jedis.append("k1","lgq");
        String appendk1 = jedis.get("k1");
        System.out.println("追加后k1="+appendk1);
        Long lenk1 = jedis.strlen("k1");
        System.out.println("追加后k1长度为："+lenk1);
        jedis.set("k1","v-11");
        System.out.println("覆盖后的k1="+jedis.get("k1"));
        Long delk1 = jedis.del("k1");
        System.out.println("删除k1受影响的key数量："+delk1);
        System.out.println(jedis.get("k1"));
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 11:29
     * @description: redis的String类型操作 02  数值加减
     */
    @Test
    void redisString02(){
        jedis.select(1);
        jedis.set("k1", "100");
        System.out.println("k1="+jedis.get("k1"));
        Long incrk1 = jedis.incr("k1");  //k1加1
        System.out.println("k1 + 1 = "+incrk1);
        Long decrk1 = jedis.decr("k1");  //k1减1
        System.out.println("k1 - 1 = "+decrk1);
        String next = "12";
        Long incrByk1 = jedis.incrBy("k1", Long.parseLong(next));  //k1加指定值
        System.out.println("k1 + "+next+" = "+incrByk1);
        next = "15";
        Long decrByk1 = jedis.decrBy("k1", Long.parseLong(next)); //k1减指定值
        System.out.println("k1 - "+next+" = "+decrByk1);
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 12:22
     * @description: redis的String类型操作 03  字符插入与获取
     */
    @Test
    void redisString03(){
        jedis.select(1);
        jedis.set("k2","hello ！");
        System.out.println("原始字符："+jedis.get("k2"));
        jedis.setrange("k2",6,"redis");
        String k2 = jedis.get("k2");
        System.out.println("插入字符后："+k2);
        String getrange = jedis.getrange("k2", 6, 10);
        System.out.println("截取插入的字符："+getrange);
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 13:41
     * @description: redis的String类型操作 04  k-v的过期时间设定
     */
    @Test
    void redisString04() throws InterruptedException {
        jedis.select(1);
        jedis.setnx("k2","v-22");
        System.out.println("setnx后k2="+jedis.get("k2"));
        Long k2 = jedis.ttl("k2");
        System.out.println("k2剩余的生存时间："+k2+"s");
        jedis.setex("k2",1,"v-setex-v2"); //k2在1秒后过期
        Long ttlk2 = jedis.ttl("k2");
        System.out.println("setex后k2剩余生存时间："+ttlk2+"s");
        System.out.println("k2="+jedis.get("k2"));
        Thread.sleep(1001);
        System.out.println("休眠1001毫秒后k2剩余生存时间："+jedis.ttl("k2")+"s");
        System.out.println("k2="+jedis.get("k2"));
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 13:55
     * @description: redis的String类型操作 05  k-v的批量存取 和 getset
     */
    @Test
    void redisString05(){
        jedis.mset("k2","v2","k3","v3","k4","v4");
        List<String> mget = jedis.mget("k2", "k3", "k4");
        Iterator<String> iterator = mget.iterator();
        while (iterator.hasNext()){
            System.out.println("+++++++++");
            System.out.println("value="+iterator.next());
        }
        jedis.msetnx("k3","v3","k4","v4-4","k5","v-5");
        List<String> mget1 = jedis.mget("k3", "k4", "k5");
        Iterator<String> iterator1 = mget1.iterator();
        while (iterator1.hasNext()){
            System.out.println("------------");
            System.out.println("value="+iterator1.next());
        }
        jedis.msetnx("k6", "v6");
        System.out.println("*******msetnx后k6="+jedis.get("k6"));
        String k4 = jedis.getSet("k4", "v4v4");
        System.out.println("k4="+k4);
        System.out.println("k4="+jedis.get("k4"));
    }


}
