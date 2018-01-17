package cn.com.navia.PhoneService.server.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorController;



@RestController
public class RequestErrorController implements ErrorController {

	@Value("${error.path:/error}")
	private String errorPath;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {

//		Enumeration<String> attrNames = request.getAttributeNames();
//		String name;
//		while (attrNames.hasMoreElements()){
//			name = attrNames.nextElement();
//			log.info("AttributeName: {}, value: {}", name, request.getAttribute(name));
//		}

		log.error("Servlet error from {}, request_uri: {}, status_code: {}, message: {}", 
				request.getRemoteAddr(), 
				request.getAttribute("javax.servlet.error.request_uri"), 
				request.getAttribute("javax.servlet.error.status_code"), 
				request.getAttribute("javax.servlet.error.message"));
		return null;
	}

	@Override
	public String getErrorPath() {
		return this.errorPath;
	}

}
