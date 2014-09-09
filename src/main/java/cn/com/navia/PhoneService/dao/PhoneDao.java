package cn.com.navia.PhoneService.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.navia.module.BaseModule;

public class PhoneDao extends BaseModule {

	@Resource(name = "phoneJdbcTemplate")
	protected JdbcTemplate phoneJdbcTemplate;

	@Override
	public void init() throws Exception {
		super.init();
		log.info("phoneJdbcTemplate url:{}", phoneJdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
	}

}
