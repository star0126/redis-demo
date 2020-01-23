package com.system.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.security.Key;
import java.util.*;


@SpringBootTest
class RedisDemoApplicationTestsHash {

    private Jedis jedis = new Jedis("127.0.0.1",6379);



    /**
     * @author: guoqing-li
     * @date: 2020/1/19 17:33
     * @description: redis的hash类型操作 01  hash的存取
     */
    @Test
    void redisHash01(){
        //存值
        jedis.hset("hash-01", "map","p1");
        //取值
        String map = jedis.hget("hash-01", "map");
        System.out.println("hash-01-map="+map);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("map-01","1");
        hashMap.put("map-02","2");
        hashMap.put("map-03","3");
        //批量存值
        jedis.hmset("hash-02",hashMap);
        //批量取值
        List<String> hmget = jedis.hmget("hash-02", "map-01", "map-02", "map-03");
        for (String s:hmget){
            System.out.println("hash-02-value="+s);
        }

    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/19 18:04
     * @description: redis的hash类型操作 02  hash获取全部值
     */
    @Test
    void redisHash02(){
        Map<String, String> map = jedis.hgetAll("hash-02");
        Set<String> keySet = map.keySet();
        for (String s:keySet){
            System.out.println("hash-02中key="+s+"，value="+map.get(s));
        }
        System.out.println("-------------hash-01和hash-02的分割---------------");
        Map<String, String> map1 = jedis.hgetAll("hash-01");
        Set<String> keySet1 = map1.keySet();
        for (String s:keySet1){
            System.out.println("hash-02中key="+s+"，value="+map1.get(s));
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 11:21
     * @description: redis的hash类型操作  03  删除指定hash中的某一对kv值
     */
    @Test
    void redisHash03(){
        Map<String, String> map = jedis.hgetAll("hash-01");
        Set<String> strings = map.keySet();
        for (String s:strings){
            System.out.println("hash-01中key="+s+"，value="+map.get(s));
        }
        System.out.println("-------------删除前后------------");
        jedis.hdel("hash-01","map");  //删除指定键值对map-value
        Map<String, String> map1 = jedis.hgetAll("hash-01");
        Set<String> strings1 = map1.keySet();
        for (String s:strings1){
            System.out.println("hash-01中key="+s+"，value="+map1.get(s));
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 11:24
     * @description: redis的hash类型操作  04 获取某一个hash的长度
     */
    @Test
    void redisHash04(){
        Long hlen = jedis.hlen("hash-02");
        System.out.println("hash的长度为："+hlen);
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 11:33
     * @description: redis的hash类型操作  05  判断某一个key在该hash中是否存在
     */
    @Test
    void redisHash05(){
        Boolean hexists = jedis.hexists("hash-02", "map-01");
        Boolean hexists1 = jedis.hexists("hash-02", "map");
        if (hexists){
            System.out.println("在hash-02中map-01存在!");
        }else {
            System.out.println("在hash-02中map-01不存在!");
        }
        if (hexists1){
            System.out.println("在hash-02中map存在！");
        }else {
            System.out.println("在hash-02中map不存在！");
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 11:39
     * @description: redis的hash类型操作  06  获取某hash中的key、value的集合
     */
    @Test
    void redisHash06(){
        Set<String> hkeys = jedis.hkeys("hash-02");
        for (String s:hkeys){
            System.out.println("hash-02中的key有，key="+s);
        }
        List<String> hvals = jedis.hvals("hash-02");
        for (String s:hvals){
            System.out.println("hash-02中的value有，value="+s);
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 11:51
     * @description: redis的hash类型操作  07  对某一值进行整数值的加减操作
     */
    @Test
    void redisHash07(){
        jedis.hincrBy("hash-02", "map-01", 1);
        Map<String, String> map = jedis.hgetAll("hash-02");
        Set<String> strings = map.keySet();
        for (String s:strings){
            System.out.println("hash-02中有key="+s+"，有value="+map.get(s));
        }
        jedis.hincrBy("hash-02","map-01",-2);
        System.out.println("-------------加减前后----------");
        Map<String, String> map1 = jedis.hgetAll("hash-02");
        Set<String> strings1 = map1.keySet();
        for (String s:strings1){
            System.out.println("hash-02中有key="+s+"，有value="+map1.get(s));
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 12:09
     * @description: redis的hash类型操作  08  对某一值进行浮点类型值的加减操作
     */
    @Test
    void redisHash08(){
        jedis.hincrByFloat("hash-02","map-01",0.5);  //浮点型值的加法操作
        Map<String, String> map = jedis.hgetAll("hash-02");
        Set<String> strings = map.keySet();
        for (String s:strings){
            System.out.println("hash-02的key="+s+"，value="+map.get(s));
        }
        System.out.println("---------浮点型数字加减前后-----------");
        jedis.hincrByFloat("hash-02","map-01",-0.5);  //浮点型值的减法操作
        Map<String, String> map1 = jedis.hgetAll("hash-02");
        Set<String> strings1 = map1.keySet();
        for (String s:strings1){
            System.out.println("hash-02的key="+s+"，value="+map1.get(s));
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 12:16
     * @description: redis的hash类型操作  09  对已有值保留原值，对不存在的值进行创建并赋值
     */
    @Test
    void redisHash09(){
        jedis.hsetnx("hash-02","map-01","v1");
        jedis.hsetnx("hash-02","map-04","v4");
        Map<String, String> map = jedis.hgetAll("hash-02");
        Set<String> strings = map.keySet();
        for (String s:strings){
            System.out.println("hash-02中的key="+s+"，value="+map.get(s));
        }
    }




}
