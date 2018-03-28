package com.tk.tms.test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.taikang.tms.bo.DataSourceConfig;
import com.taikang.tms.service.IBaseService;
import com.taikang.tms.util.JedisUtils;
import com.taikang.tms.util.SerializeUtils;
import com.taikang.tms.util.SwitchUtils;

/**
 * 
 * DataSourceConfigMapper.xml配置文件中增加配置：<cache type="com.taikang.tms.cache.QRedisCache" />
 * 每一条SQL中，根据 useCache="false/true"的配置，决定是否将使用redis作为二级缓存
 * 
 * @author 
 * @version [版本号，默认V1.0.0]
 * @Credited 2016年12月8日 下午1:20:16
 * @see       [相关类/方法]
 * @since     [产品/模块版本]
 */
public class ServiceTest extends BaseJunit4Test {
	@Autowired
	private IBaseService baseService ;
	
	/**
	 * 测试查询所有数据
	 * useCache="false" 将不走缓存
	 */
	@Test
	public void selectAllTest(){
		List<DataSourceConfig> dataSourceConfiglist = baseService.selectAllDataSource();
		for(DataSourceConfig ds : dataSourceConfiglist){
			System.out.println(ds.toString());
		}
		if(dataSourceConfiglist != null){
			assertEquals(2, dataSourceConfiglist.size());
		}else{
			assertEquals(null, dataSourceConfiglist);
		}
	}
	
	/**
	 * 测试查询单条数据
	 * useCache="false" 将走缓存
	 */
	@Test
	public void selectOneTest(){
		
		SwitchUtils.setSwitch(true);
		DataSourceConfig dc = baseService.selectDataSourceByManageCom("C");
		System.out.println("dc = " + dc.toString());
		
		DataSourceConfig d1 = baseService.selectDataSourceByManageCom("1");
		System.out.println("d1 = " + d1.toString());
		
		DataSourceConfig da = baseService.selectDataSourceByManageCom("A");
		
		assertEquals("山东分公司", dc.getManagename());
		assertEquals("北京分公司", d1.getManagename());
		assertEquals(null, da);
	}
	
	@Test
	public void redisDownTest(){
		SwitchUtils.setSwitch(true);
		
		DataSourceConfig dc = new DataSourceConfig();
		for(int i = 0 ; i < 100 ; i++){
			dc = baseService.selectDataSourceByManageCom("C");
			System.out.println("dc = " + dc.toString());
		}
	}
	/**
	 * 测试开关打开：将走redis缓存
	 */
	@Test
	public void openAndselectOneTest(){
		SwitchUtils.setSwitch(true);
		this.selectOneTest();
	}
	
	/**
	 * 测试开关打开：将走redis缓存
	 */
	@Test
	public void closeAndselectOneTest(){
		SwitchUtils.setSwitch(false);
		this.selectOneTest();
	}
	
	/**
	 * 插入单条数据
	 * 插入后，DataSourceConfigMapper.xml内所有的缓存将被删除
	 */
	@Test
	public void insertTest(){
		DataSourceConfig config = new DataSourceConfig();
		config.setManagecom("2");
		config.setDataSourceName("dataSource_2");
		config.setManagename("湖北");
		
		baseService.insertDataSource(config);
	}
	
	/**
	 * 更新数据
	 * 更新后，DataSourceConfigMapper.xml内所有的缓存将被删除
	 */
	@Test
	public void updateTest(){
		List<DataSourceConfig> dataSourceConfiglist = baseService.selectAllDataSource();
		
		for(DataSourceConfig ds : dataSourceConfiglist){
			if(ds != null && "2".equals(ds.getManagecom())){
				ds.setManagename("湖北分公司");
				
				baseService.updateDataSource(ds);
			}
		}
	}
	
	/**
	 * 删除数据
	 * 删除后，DataSourceConfigMapper.xml内所有的缓存将被删除
	 */
	@Test
	public void deleteTest(){
		List<DataSourceConfig> dataSourceConfiglist = baseService.selectAllDataSource();
		
		for(DataSourceConfig ds : dataSourceConfiglist){
			if(ds != null && "2".equals(ds.getManagecom())){
				baseService.deleteDataSource(ds);
			}
		}
	}
}
