package com.taikang.tms.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisUtils {
	 
	private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
	
    private static JedisPool JEDISPOOL;
     
    /*static {
        Properties props = new Properties();
        try {
            props.load(JedisUtils.class.getResourceAsStream("/properties/redis.properties"));
            JedisPoolConfig conf = new JedisPoolConfig();
            conf.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));   
            conf.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));   
            conf.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));   
            JEDISPOOL = new JedisPool(conf, props.getProperty("redis.ip"), Integer.valueOf(props.getProperty("redis.port")));
        } catch (IOException e) {
            //logger.error("加载[jedis.properties]异常[" + e.getMessage() + "]", e);
        }
    }
     */
    
    public static void getInstance(){
    	if(JEDISPOOL == null){
    		logger.info(" JedisUtils getInstance ");
        	Properties props = new Properties();
            try {
            	//TODO:配置文件固定，换成使用spring加载的方式
                props.load(JedisUtils.class.getResourceAsStream("/properties/redis.properties"));
                JedisPoolConfig conf = new JedisPoolConfig();
                conf.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));   
                conf.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));   
                conf.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));
                JEDISPOOL = new JedisPool(conf, props.getProperty("redis.ip"), Integer.valueOf(props.getProperty("redis.port")));
            } catch (IOException e) {
                logger.error("加载[jedis.properties]异常[" + e.getMessage() + "]", e);
            }
    	}
    }
    
    
    
    public static Jedis getJedis() {
    	try{
    	      return JEDISPOOL.getResource();
	    }catch (Exception e) {
	    	  //throw new JedisConnectionException("Could not get a resource from the pool");
	    	  return null;
	    }
    }
     
    public static void recycleJedis(Jedis jedis) {
    	jedis.close();
    }
    
    public static void closeJedisPool(){
    	if(JEDISPOOL != null){
    		JEDISPOOL.close();
    	}
    }
     
    /**
     * Redis存储Object序列化流
     * */
    public static void put(Object key, Object value) {
    	try{
	        Jedis jedis = getJedis();
	        logger.info(" redis put ... key = [" + key + "]");
	        jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(value));
	        //TODO:考虑用切片处理
	        SwitchUtils.setSucc();
    	}catch(Exception e){
    		//TODO:异常放在utils中捕捉，还是在NTSRedisCache捕捉
    		//TODO:统一异常处理方法
	        SwitchUtils.setFailCount();
    		logger.error("Redis执行异常[" + e.getMessage() + "]" , e);
    	}
    }
     
    public static <T> T get(Object key) {
    	try{
            Jedis jedis = getJedis();
            T value = SerializeUtils.unserialize(jedis.get(SerializeUtils.serialize(key)));
	        logger.info(" redis get ... key = [" + key + "] , value = [" + value + "]");
            recycleJedis(jedis);
	        SwitchUtils.setSucc();
            return value;
    	}catch(Exception e){
	        SwitchUtils.setFailCount();
    		logger.error("Redis执行异常[" + e.getMessage() + "]" , e);
    	}
    	return null;
    }
     
    public static Long remove(Object key) {
    	try{
	        Jedis jedis = getJedis();
	        Long num = jedis.del(SerializeUtils.serialize(key));
	        recycleJedis(jedis);
	        SwitchUtils.setSucc();
	        return num;
    	}catch(Exception e){
	        SwitchUtils.setFailCount();
    		logger.error("Redis执行异常[" + e.getMessage() + "]" , e);
    	}
    	return 0L;
    }
     
    public static void removeAll() {
    	try{
	        Jedis jedis = getJedis();
	        jedis.flushDB();
	        recycleJedis(jedis);
	        SwitchUtils.setSucc();
		}catch(Exception e){
	        SwitchUtils.setFailCount();
    		logger.error("Redis执行异常[" + e.getMessage() + "]" , e);
		}
    }
}