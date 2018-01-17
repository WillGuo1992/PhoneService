package cn.com.navia.PhoneService.assist;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ResourceUtil implements ResourceLoaderAware {
	private ResourceLoader resourceLoader;

	@Override
	public void setResourceLoader(ResourceLoader rsld) {
		resourceLoader = rsld;
	}

	public Resource getResource(String location) {
		return resourceLoader.getResource(location);
	}
}
