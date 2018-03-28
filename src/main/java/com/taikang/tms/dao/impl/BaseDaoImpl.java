package com.taikang.tms.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.taikang.tms.bo.DataSourceConfig;
import com.taikang.tms.dao.IBaseDao;

@Repository(IBaseDao.DAO_ID)
public class BaseDaoImpl implements IBaseDao {
	private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;

	@Override
	public List<DataSourceConfig> selectAllDataSource() {
		//TODO:在控制台中打印sql语句
		return sqlSession.selectList("selectAllConfig");
	}

	@Override
	public DataSourceConfig selectDataSourceByManageCom(String managecom) {
		return sqlSession.selectOne("selectConfigByManagecom", managecom);
	}

	@Override
	public void insertDataSource(DataSourceConfig config) {
		sqlSession.insert("addconfig" , config);
	}

	@Override
	public void updateDataSource(DataSourceConfig config) {
		sqlSession.update("updateconfig", config);
	}

	@Override
	public void deleteDataSource(DataSourceConfig config) {
		sqlSession.delete("deleteconfig" , config);
	}

}
