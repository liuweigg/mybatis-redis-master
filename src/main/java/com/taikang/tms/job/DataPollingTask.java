package com.taikang.tms.job;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taikang.tms.bo.DataSourceConfig;
import com.taikang.tms.service.IBaseService;

/**
 * 
 * 定时任务：将分公司符合条件的数据抽取到总公司库中
 * @author liuwei117
 * @version [版本号，默认V1.0.0]
 * @Credited 2016年9月29日 上午11:05:28
 * @see       [相关类/方法]
 * @since     [产品/模块版本]
 */
public class DataPollingTask{
	

	private static final Logger LOGGER = LoggerFactory.getLogger(DataPollingTask.class);
	
	@Resource(name=IBaseService.SERVICE_ID)
	private IBaseService baseService;

	
	public void  execute(){
		//进入定时任务
		LOGGER.info("DataPollingTask | 进入定时任务... ");
		
		List<DataSourceConfig> list = baseService.selectAllDataSource();
		DataSourceConfig d1 = baseService.selectDataSourceByManageCom("C");
		
	}
}
