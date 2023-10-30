package com.assetmaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAspectJAutoProxy
@SpringBootApplication
public class AssetMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetMasterApplication.class, args);
	}

	@Bean
	public Logger logger() {
		return LoggerFactory.getLogger(AssetMasterApplication.class);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/asset-master/**").allowedOrigins("http://localhost:8080");
//			}
//		};
//	}

}
