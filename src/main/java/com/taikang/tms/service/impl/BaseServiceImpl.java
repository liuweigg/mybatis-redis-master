package com.taikang.tms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taikang.tms.bo.DataSourceConfig;
import com.taikang.tms.dao.IBaseDao;
import com.taikang.tms.service.IBaseService;


@Service(IBaseService.SERVICE_ID)
public class BaseServiceImpl implements IBaseService {
	@Resource(name=IBaseDao.DAO_ID)
	private IBaseDao baseDao;

	@Override
	public List<DataSourceConfig> selectAllDataSource() {
		// TODO Auto-generated method stub
		return baseDao.selectAllDataSource();
	}
	
	@Override
	public DataSourceConfig selectDataSourceByManageCom(String managecom) {
		// TODO Auto-generated method stub
		return baseDao.selectDataSourceByManageCom(managecom);
	}

	@Override
	public void insertDataSource(DataSourceConfig config) {
		// TODO Auto-generated method stub
		baseDao.insertDataSource(config);
	}

	@Override
	public void updateDataSource(DataSourceConfig config) {
		// TODO Auto-generated method stub
		baseDao.updateDataSource(config);
	}

	@Override
	public void deleteDataSource(DataSourceConfig config) {
		// TODO Auto-generated method stub
		baseDao.deleteDataSource(config);
	}
}
