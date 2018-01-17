package cn.com.navia.PhoneService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * App
 *
 */  


@Configuration
@EnableAutoConfiguration
// @SpringApplicationConfiguration
// @PropertySource("application.properties")
@ImportResource({ "classpath*:spring.xml" })
public class App 
{
 
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
 
    public static void main( String[] args )
    {
    	App app = new App();
    	app.start(args);
    	app.log.info("PhoneService {} startup successfully", app.getClass().getSimpleName()  );
    }

    private void start(String[] args) {
		try {
			SpringApplication app = new SpringApplication(this.getClass());
			ConfigurableApplicationContext confCtx = app.run(args);
			this.log.info("ConfigurableApplicationContext: {}", confCtx);
			
		} catch (Exception e) {
			log.error("PhoneService startup error: {}", e.getMessage());
			System.exit(-1);
		}
	}
}
