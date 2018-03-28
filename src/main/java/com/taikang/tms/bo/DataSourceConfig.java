package com.taikang.tms.bo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
/**
 * 
 * @author t-wangjingwei
 * 数据库中 分公司与数据源的对应表
 */
@Alias("dataSourceConfig")
public class DataSourceConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String managecom;
	private String dataSourceName;
	private String managename;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManagecom() {
		return managecom;
	}

	public void setManagecom(String managecom) {
		this.managecom = managecom;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getManagename() {
		return managename;
	}

	public void setManagename(String managename) {
		this.managename = managename;
	}

	@Override
	public String toString() {
		return "DataSourceConfig [id=" + id + ", managecom=" + managecom
				+ ", dataSourceName=" + dataSourceName + ", managename="
				+ managename + "]";
	}
}
