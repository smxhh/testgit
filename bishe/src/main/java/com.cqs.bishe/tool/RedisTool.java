package com.cqs.bishe.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by cqs on 16-6-5.
 */



public class RedisTool {

    private static Logger logger = LoggerFactory.getLogger(RedisTool.class);

    private static volatile RedisTool redisTool ;
    private static final String redisServerIp="redis_server_ip";

    private  Jedis jedis;



    private RedisTool(String serverIp){
        try {
            //连接本地的 Redis 服务
            jedis = new Jedis(serverIp);
            logger.info("Connection to server sucessfully");
            //查看服务是否运行
            logger.info("Server is running: " + jedis.ping());
        }catch (Exception e){
            logger.error("redis connect fail ,error:{}",e);
        }
    }

    public static RedisTool getInstance(){
        return getInstance(PropertyTool.getInstance().getValue(redisServerIp));
    }

    public static RedisTool getInstance(String serverIp){
        if(redisTool == null){
            return getRedisToolSafe(serverIp);
        }
        return redisTool;
    }

    private static synchronized RedisTool getRedisToolSafe(String serverIp){
        if(redisTool ==null){
            redisTool = new RedisTool(serverIp);
        }
        return redisTool;
    }


    public String getCode(String mobile) {
        return jedis.get(mobile);
    }

    public void put(String key,String value,int time){
        put(key, value);
        jedis.expire(key,time);
    }

    public void put(String key,String value){
        jedis.set(key,value);
    }


    public String getValue(String key){
        return jedis.get(key);
    }
}
