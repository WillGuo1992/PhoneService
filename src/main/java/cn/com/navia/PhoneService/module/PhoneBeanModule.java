package cn.com.navia.PhoneService.module;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;

public abstract class PhoneBeanModule extends ApplicationObjectSupport implements PhoneModule {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	@PostConstruct
	public void init() throws Exception {
		log.info("{}:init", this.getClass().getSimpleName());
	}

	@Override
	@PreDestroy
	public void destory() {
		log.debug("{}:destory", this.getClass().getSimpleName());
	}

	@Override
	public void ready() throws Exception {
		log.debug("{}:ready", this.getClass().getSimpleName());
	}
}
