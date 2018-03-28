package com.taikang.tms.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taikang.tms.util.JedisUtils;
import com.taikang.tms.util.SwitchUtils;

public class NTSRedisCache implements Cache {
	
	private static Logger logger = LoggerFactory.getLogger(NTSRedisCache.class);
	 
    private String cacheId;
     
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
     
    public NTSRedisCache(String cacheId) {
        if (cacheId == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.cacheId = cacheId;
        
        if(SwitchUtils.redisSwitch){
        	JedisUtils.getInstance();
        }
    }
     
    @Override
    public String getId() {
        return cacheId;
    }
 
    @Override
    public void putObject(Object key, Object value) {
    	//TODO:开关采用一个静态变量，每个需要使用redis的地方，都判断这个变量，比较繁琐
    	if(SwitchUtils.redisSwitch){
    		JedisUtils.put(key, value);
    	}
    }
 
    @Override
    public Object getObject(Object key) {
    	if(SwitchUtils.redisSwitch){
    		return JedisUtils.get(key);
    	}else{
    		return null;
    	}
    }
 
    @Override
    public Object removeObject(Object key) {
    	if(SwitchUtils.redisSwitch){
    		return JedisUtils.remove(key);
    	}else{
    		return null;
    	}
    }
 
    @Override
    public void clear() {
    	if(SwitchUtils.redisSwitch){
    		JedisUtils.removeAll();
    	}
    }
 
    @Override
    public int getSize() {
        return 0;
    }
 
    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
 
}