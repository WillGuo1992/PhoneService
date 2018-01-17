package cn.com.navia.PhoneService.route;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix="trhub.route")
public class RouteDataArray {

	private List<String> json = new ArrayList<String>();

	public List<String> getJson() {
		return json;
	}

	public void setJson(List<String> json) {
		this.json = json;
	}

}
