package com.taikang.tms.dao;

import java.util.List;

import com.taikang.tms.bo.DataSourceConfig;

public interface IBaseDao {
	final String DAO_ID = "baseDao";

	List<DataSourceConfig> selectAllDataSource();
	DataSourceConfig selectDataSourceByManageCom(String managecom);
	
	void insertDataSource(DataSourceConfig config);
	void updateDataSource(DataSourceConfig config);
	void deleteDataSource(DataSourceConfig config);
}
