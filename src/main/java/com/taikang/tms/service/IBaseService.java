package com.taikang.tms.service;

import java.util.List;

import com.taikang.tms.bo.DataSourceConfig;


public interface IBaseService {
	final String SERVICE_ID = "BaseService";
	
	List<DataSourceConfig> selectAllDataSource();
	DataSourceConfig selectDataSourceByManageCom(String managecom);
	
	void insertDataSource(DataSourceConfig config);
	void updateDataSource(DataSourceConfig config);
	void deleteDataSource(DataSourceConfig config);
}
