package com.system.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


@SpringBootTest
class RedisDemoApplicationTestsSet {

    private Jedis jedis = new Jedis("127.0.0.1",6379);

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 20:51
     * @description: redis的set类型操作  01  set的创建与set的value值的获取
     */
    @Test
    void redisSet01(){
        //给创建set并添加元素
        jedis.sadd("set-01","a","b","c","d","e");
        //获取set里面的value值
        Set<String> smembers = jedis.smembers("set-01");
        Iterator<String> iterator = smembers.iterator();
        while (iterator.hasNext()){
            System.out.println("set-01-value="+iterator.next());
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 20:55
     * @description: redis的set类型操作  02  判断set里面是否存在某值和set的长度获取
     */
    @Test
    void redisSet02(){
        if (jedis.sismember("set-01","a")){
            System.out.println("set-01中存在a");
        }else {
            System.out.println("set-01中不存在a");
        }
        if (jedis.sismember("set-01","0")){
            System.out.println("set-01中存在0");
        }else {
            System.out.println("set-01中不存在0");
        }
        Long scard = jedis.scard("set-01");
        System.out.println("set-01的长度为："+scard);
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 21:02
     * @description: redis的set类型操作  03  删除set里的值
     */
    @Test
    void redisSet03(){
        for (String s:jedis.smembers("set-01")){
            System.out.println("set-01-value="+s);
        }
        jedis.srem("set-01","a","b","0");
        System.out.println("-------------删除前后-----------");
        for (String s:jedis.smembers("set-01")){
            System.out.println("set-01-value="+s);
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 21:09
     * @description: redis的set类型操作  04  随机获取set中指定数量个value值
     */
    @Test
    void redisSet04(){
        List<String> srandmember = jedis.srandmember("set-01", 2);
        for(String s:srandmember){
            System.out.println(s);
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 21:10
     * @description: redis的set类型操作  05  随机出栈，可以指定出栈的个数
     */
    @Test
    void redisSet05(){
        Set<String> smembers = jedis.smembers("set-01");
        for (String s:smembers){
            System.out.println("set-01-value="+s);
        }
        System.out.println("-------------随机出栈前后------------");
        //随机出栈
        jedis.spop("set-01", 2);
        Set<String> smembers1 = jedis.smembers("set-01");
        for (String s:smembers1){
            System.out.println("set-01-value="+s);
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 21:18
     * @description: redis的set类型操作  06  指定将一个set中的某个value值赋值给另一个set
     */
    @Test
    void redisSet06(){
        Set<String> smembers = jedis.smembers("set-01");
        Set<String> smembers1 = jedis.smembers("set-02");
        for (String s:smembers){
            System.out.println("set-01-value="+s);
        }
        System.out.println("************************");
        for (String s:smembers1){
            System.out.println("set-02-value="+s);
        }
        jedis.smove("set-01","set-02","d");
        Set<String> smembers2 = jedis.smembers("set-01");
        Set<String> smembers3 = jedis.smembers("set-02");
        System.out.println("***************赋值前后**************");
        for (String s:smembers2){
            System.out.println("set-01-value="+s);
        }
        System.out.println("----------------------------");
        for (String s:smembers3){
            System.out.println("set-02-value="+s);
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 21:31
     * @description: redis的set类型操作  07  交集、差集、并集
     */
    @Test
    void redisSet07(){
        //差集
        Set<String> sdiff = jedis.sdiff("set-01", "set-02");
        System.out.println("set-01和set-02的差集是："+sdiff);
        //交集
        Set<String> sinter = jedis.sinter("set-01", "set-02");
        System.out.println("set-01和set-02的交集是："+sinter);
        //并集
        Set<String> sunion = jedis.sunion("set-02", "set-02");
        System.out.println("set-01和set-02的并集是："+sunion);
    }

}
