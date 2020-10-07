package com.bolsadeideas.springboot.jpa.app;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcController implements WebMvcConfigurer {
	
	
	private static final Logger logger = LoggerFactory.getLogger(MvcController.class);

	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);

		//registry.addResourceHandler("/uploads/**").addResourceLocations("file:/H:/SpringBootCourse/uploads/");
		
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();

		logger.info("resourcePath: " + resourcePath);
		
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
	}*/

}
