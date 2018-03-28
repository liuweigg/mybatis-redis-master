package com.taikang.tms.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taikang.tms.util.JedisUtils;
import com.taikang.tms.util.SwitchUtils;

public class NTSRedisCacheNoSwitch implements Cache {
	
	private static Logger logger = LoggerFactory.getLogger(NTSRedisCacheNoSwitch.class);
	 
    private String cacheId;
     
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
     
    public NTSRedisCacheNoSwitch(String cacheId) {
        if (cacheId == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.cacheId = cacheId;
        
        JedisUtils.getInstance();
    }
     
    @Override
    public String getId() {
    	//logger.info("NTSRedisCache cacheId = " + cacheId);
        return cacheId;
    }
 
    @Override
    public void putObject(Object key, Object value) {
    	JedisUtils.put(key, value);
    }
 
    @Override
    public Object getObject(Object key) {
    	return JedisUtils.get(key);
    }
 
    @Override
    public Object removeObject(Object key) {
    	return JedisUtils.remove(key);
    }
 
    @Override
    public void clear() {
    	JedisUtils.removeAll();
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