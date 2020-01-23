package com.system.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;


@SpringBootTest
class RedisDemoApplicationTestsTransaction {

    private Jedis jedis = new Jedis("127.0.0.1",6379);

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 15:33
     * @description: redis中事务的基本操作 01 事务的正常开始和正常结束
     */
    @Test
    void redisTransaction01(){
        Transaction multi = jedis.multi();  //开始事务
        multi.set("k1","v1");
        multi.set("k2","v2");
        multi.exec();  //结束事务
        //检验事务
        Set<String> keys = jedis.keys("*");
        for (String s:keys){
            System.out.println("事务执行后有key="+s+"，value="+jedis.get(s));
        }
    }

    /**
     * @author: guoqing-li
     * @create: 2020/1/22 15:42
     * @description: redis中事务的基本操作 02 事务开始然后放弃
     */
    @Test
    void redisTransaction02(){
        Transaction multi = jedis.multi();  //开始事务
        multi.set("k3","v3");
        multi.set("k4","v4");
        multi.discard();  //放弃事务
        //检验事务
        Set<String> keys = jedis.keys("*");
        for (String s:keys){
            System.out.println("事务执行后有key="+s+"，value="+jedis.get(s));
        }
    }

    int balance;  //可用余额
    int debt;  //支出金额
    /**
     * @author: guoqing-li
     * @create: 2020/1/22 16:16
     * @description: redis中事务的基本操作 03 模拟银行刷款
     */
    @Test
    void redisTransaction03(){
        jedis.watch("balance");  //开启对余额的监控
        int amtToSubtract=10;  //实刷金额
        int balance = Integer.parseInt(jedis.get("balance"));
        if (balance<amtToSubtract){
            jedis.unwatch();  //放弃对余额的监控
            System.out.println("余额不足，请充值！");
        }else {
            System.out.println("__________________进入刷款___________________");
            Transaction multi = jedis.multi(); //开启事务
            multi.decrBy("balance",amtToSubtract);
            multi.incrBy("debt",amtToSubtract);
            multi.exec();
            System.out.println(new Date().toString()+"您的账户支出"+amtToSubtract+"元");
            System.out.println("可用余额为："+jedis.get("balance"));
            System.out.println("截至目前您已支出："+jedis.get("debt"));
        }
    }




}
