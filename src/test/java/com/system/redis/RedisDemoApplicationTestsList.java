package com.system.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


@SpringBootTest
class RedisDemoApplicationTestsList {

    private Jedis jedis = new Jedis("127.0.0.1",6379);

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 16:21
     * @description: redis的list类型操作 01 list元素存取
     */
    @Test
    void redisList01(){
        System.out.println("-------------list-01--------------");
        jedis.lpush("list-01", "1", "3", "5", "7", "9");  //从左侧开始入栈，先入后出
        jedis.rpush("list-02","0","2","4","6","8");  //从右侧开始入栈，先入后出
        List<String> list01 = jedis.lrange("list-01", 0, -1);
        Iterator<String> iterator = list01.iterator();
        while (iterator.hasNext()){
            System.out.println("list-01-value="+iterator.next());
        }
        Long llen01 = jedis.llen("list-01");
        System.out.println(">>>>长度："+llen01);
        System.out.println("-------------list-02--------------");
        List<String> list02 = jedis.lrange("list-02", 0, -1);
        Iterator<String> iterator1 = list02.iterator();
        while (iterator1.hasNext()){
            System.out.println("list-02-value="+iterator1.next());
        }
        Long llen02 = jedis.llen("list-02");
        System.out.println(">>>>长度："+llen02);
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 16:36
     * @description: redis的list类型操作 02 弹栈
     */
    @Test
    void redisList02(){
        String lpop = jedis.lpop("list-01");  //弹出栈顶数据
        System.out.println("lpop弹栈："+lpop);
        String rpop = jedis.rpop("list-01");  //弹出栈底数据
        System.out.println("rpop弹栈："+rpop);
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 17:23
     * @description: redis的list类型操作 03  根据索引获取value
     */
    @Test
    void redisList03(){
        String lindex0 = jedis.lindex("list-02", 0);
        System.out.println("lindex0="+lindex0);
        String lindex1 = jedis.lindex("list-02", 1);
        System.out.println("lindex1="+lindex1);
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 17:34
     * @description: redis的list类型操作 04  删除指定数量个指定value值的元素，在指定的list中
     */
    @Test
    void redisList04(){
        jedis.lpush("list-03","0","0","0","1","1","0","3","4","6","0");
        List<String> lrange = jedis.lrange("list-03", 0, -1);
        Iterator<String> iterator = lrange.iterator();
        while (iterator.hasNext()){
            System.out.println("list-01-value="+iterator.next());
        }
        jedis.lrem("list-03", 4, "0"); //删掉4个value为0的元素值，在list-03集合中
        List<String> lrange1 = jedis.lrange("list-03", 0, -1);
        Iterator<String> iterator1 = lrange1.iterator();
        System.out.println("*************删除前后分割*****************");
        while (iterator1.hasNext()){
            System.out.println("list-01-value="+iterator1.next());
        }
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 17:53
     * @description: redis的list类型操作 05 截取list指定索引范围的值并将其重置为该截取值
     */
    @Test
    void redisList05(){
        List<String> lrange = jedis.lrange("list-02", 0, -1);
        Iterator<String> iterator = lrange.iterator();
        while (iterator.hasNext()){
            System.out.println("list-02-value="+iterator.next());
        }
        System.out.println("***********************截取前后分割***********************");
        jedis.ltrim("list-02",3,4);
        List<String> lrange1 = jedis.lrange("list-02", 0, -1);
        Iterator<String> iterator1 = lrange1.iterator();
        while (iterator1.hasNext()){
            System.out.println("list-02-value="+iterator1.next());
        }
    }

    /**
     * @author: guoqing-li
     * @date: 2020/1/17 18:03
     * @description: redis的list类型操作 06 将指定list的栈底弹出去并赋给另一个list的栈顶
     */
    @Test
    void redisList06(){
        List<String> lrange1 = jedis.lrange("list-02", 0, -1);
        Iterator<String> iterator1 = lrange1.iterator();
        while (iterator1.hasNext()){
            System.out.println("list-02-value="+iterator1.next());
        }
        jedis.rpoplpush("list-01", "list-02");
        List<String> lrange = jedis.lrange("list-02", 0, -1);
        System.out.println("__________________弹栈并赋值给另一个list前后_______________");
        for (String s:lrange){
            System.out.println("list-02-value="+s);
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 19:46
     * @description:  redis的list类型操作 07  将指定list的指定index的值赋为指定的值
     */
    @Test
    void redisList07(){
        jedis.lpush("list-01","0","1","2","3","4","5","6","7");
        List<String> lrange = jedis.lrange("list-01", 0, -1);
        for (String s:lrange){
            System.out.println("list-01-value = "+s);
        }
        jedis.lset("list-01",0,"-1");
        List<String> lrange1 = jedis.lrange("list-01", 0, -1);
        System.out.println("--------------------对指定list的指定索引赋值前后--------------------");
        for (String s:lrange1){
            System.out.println("list-01-value = "+s);
        }

    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/18 20:17
     * @description:   redis的list类型操作 08  在指定list指定值的前后添加具体值
     */
    @Test
    void redisList08(){
        List<String> lrange = jedis.lrange("list-01", 0, -1);
        for (String s:lrange){
            System.out.println("list-01-value="+s);
        }
        jedis.linsert("list-01", ListPosition.AFTER, "3", "0");
        System.out.println("_____________________添加值前后________________________");
        for (String s:jedis.lrange("list-01",0,-1)){
            System.out.println("list-01-value="+s);
        }
    }


}
