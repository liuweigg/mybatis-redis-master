package com.taikang.tms.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchUtils {
	private static Logger logger = LoggerFactory.getLogger(SwitchUtils.class);
	
	public static boolean redisSwitch = SwitchUtils.getSwitch() ;
	
	public static int fail_count = 0 ;
	
	public static boolean getSwitch(){
		Properties props = new Properties();
		try{
			props.load(JedisUtils.class.getResourceAsStream("/properties/redis.properties"));
	        boolean redisSwitch = Boolean.valueOf(props.getProperty("redis.switch"));
	        
	        return redisSwitch ;
		}catch(Exception e){
			return false ;
		}
	}
	
	public static void setSwitch(boolean redisSwitch){
		if(true == SwitchUtils.redisSwitch && false == redisSwitch){
			//switch : open --> close
			logger.info("switch : open --> close");
			JedisUtils.closeJedisPool();
		}else if(false == SwitchUtils.redisSwitch && true == redisSwitch){
			//switch : close --> open
			logger.info("switch : close --> open");
			JedisUtils.getInstance();
		}
		
		SwitchUtils.redisSwitch = redisSwitch ;
    }
	
	//TODO:当redis连接异常超过一定数量之后， 不再走redis,但是没有一个机制，当redis恢复之后会重新使用redis
	public static void setFailCount(){
		if(redisSwitch){
			fail_count += 1;
			
			//TODO:失败数量配置到配置文件中
			if(fail_count > 10){
				logger.info(" setSwitch(false) ");
				setSwitch(false);
			}
		}
	}
	
	public static void setSucc(){
		if(fail_count > 0 ){
			fail_count = 0 ;
		}
		
		if(!redisSwitch){
			setSwitch(true);
		}
	}
}
