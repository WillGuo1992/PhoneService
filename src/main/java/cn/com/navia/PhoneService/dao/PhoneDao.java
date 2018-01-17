package cn.com.navia.PhoneService.dao;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.navia.PhoneService.module.PhoneBaseModule;


public class PhoneDao extends PhoneBaseModule {

	@Resource(name = "phoneJdbcTemplate")
	protected JdbcTemplate phoneJdbcTemplate;

	@Resource(name = "mallJdbcTemplate")
	protected JdbcTemplate mallJdbcTemplate;

	@Resource(name = "stringRedisTemplate")
	protected StringRedisTemplate phoneStrRedisTemplate;

	@Value("${phone.redis.dbIndex}")
	private int dbIndex;

	@Override
	public void init() throws Exception {
		super.init();
		JedisConnectionFactory jcf = (JedisConnectionFactory) phoneStrRedisTemplate.getConnectionFactory();
		jcf.setDatabase(dbIndex);
		log.info("phoneJdbcTemplate url: {}", phoneJdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
		log.info("mallJdbcTemplate url: {}", mallJdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
		log.info("phoneStrRedisTemplate at: {}:{}, dbIndex: {}", jcf.getHostName(), jcf.getPort(), jcf.getDatabase());
	}

}
