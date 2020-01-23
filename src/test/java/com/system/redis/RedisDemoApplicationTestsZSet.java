package com.system.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;


@SpringBootTest
class RedisDemoApplicationTestsZSet {

    private Jedis jedis = new Jedis("127.0.0.1",6379);

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 12:59
     * @description: redis的zset类型操作  01  zset类型数据的存取
     */
    @Test
   void redisZSet01(){
       jedis.zadd("zset-01",1,"set-1");
       Set<String> zrange = jedis.zrange("zset-01", 0, -1);
       for (String s:zrange){
           System.out.println("zset-01有值："+s);
       }

       Map map = new HashMap();
       map.put("v-1",1.0);
       map.put("v-2",2.0);
       map.put("v-3",3.0);
       jedis.zadd("zset-02",map);

        Set<String> zrange1 = jedis.zrange("zset-02", 0, -1);
        for (String s:zrange1){
            System.out.println("zset-02有值："+s);
        }

    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 13:17
     * @description: redis的zset类型操作  02  获取score某个值段间的value
     */
    @Test
    void redisZSet02(){
        Set<String> strings = jedis.zrangeByScore("zset-02", 1.0, 3.0);
        for (String s:strings){
            System.out.println("在zset-02中从scores1.0-3.0有值"+s);
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 13:52
     * @description: redis的zset类型操作  03  获取某个zset里面的score和element的值
     */
    @Test
    void redisZSet03(){
        Set<Tuple> tuples = jedis.zrangeWithScores("zset-02", 0, -1);
        for (Tuple t:tuples){
            System.out.println("zset-02中有：score="+t.getScore()+"，element="+t.getElement());
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 13:59
     * @description: redis的zset类型操作  04  删除某个zset的值
     */
    @Test
    void redisZSet04(){
        Set<Tuple> tuples = jedis.zrangeWithScores("zset-02", 0, -1);
        for (Tuple t:tuples){
            System.out.println("zset-02中：score="+t.getScore()+"，element="+t.getElement());
        }
        System.out.println("-------------删除前后分界-------------");
        jedis.zrem("zset-02","v-3");  //删除
        Set<Tuple> tuples1 = jedis.zrangeWithScores("zset-02", 0, -1);
        for (Tuple t:tuples1){
            System.out.println("zset-02中：score="+t.getScore()+"，element="+t.getElement());
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 14:39
     * @description: redis的zset类型操作  05  获取zset集合的长度、获取zset集合指定score段间的长度
     */
    @Test
    void redisZSet05(){
        Long zcard = jedis.zcard("zset-02");
        System.out.println("zset-02的长度为："+zcard);
        Long zcount = jedis.zcount("zset-02", 1.0, 2.0);
        System.out.println("zset-02中score从1.0到2.0的长度为："+zcount);
    }


    /**
     * @author: guoqing-li
     * @create: 2020/1/22 15:00
     * @description: redis的zset类型操作  06  根据条件逆序获取zset里面的值
     */
    @Test
    void redisZSet06(){
        //逆序获得zset中某元素的下标值 从0开始
        Long zrevrank = jedis.zrevrank("zset-02", "v-2");
        System.out.println("逆序时v-2的下标值是："+zrevrank);
        //逆序获取zset里面的元素值以及元素的score
        System.out.println("---------------逆序获取zset-02的元素---------------");
        Set<Tuple> tuples = jedis.zrevrangeWithScores("zset-02", 0, -1);
        for (Tuple t:tuples){
            System.out.println("zset-02的值score="+t.getScore()+"，element="+t.getElement());
        }
        //根据score逆序获取zset里面元素值及元素的score
        Set<Tuple> tuples1 = jedis.zrevrangeByScoreWithScores("zset-02", 5.0, 1.0);
        System.out.println("--------------逆序时score在5.0到1.0之间的元素及score值----------------");
        for (Tuple t:tuples1){
            System.out.println("zset-02中scoreMax=5.0,scoreMin=1.0时有：score="+t.getScore()+"，element="+t.getElement());
        }
    }
}
