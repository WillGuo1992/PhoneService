package cn.com.navia.PhoneService.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.navia.PhoneService.module.PhoneBaseModule;


public class PhoneDao extends PhoneBaseModule {

	@Resource(name = "phoneJdbcTemplate")
	protected JdbcTemplate phoneJdbcTemplate;

	@Override
	public void init() throws Exception {
		super.init();
		log.info("phoneJdbcTemplate url:{}", phoneJdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
	}

}
