package com.cxm.CorsConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解決跨域問題
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		System.out.print("##############CORS_Config##############");
		registry.addMapping("/**").allowedOrigins("http://localhost:8080")// 指定只能誰用
				.allowedMethods("*").allowedHeaders("*").allowCredentials(true).maxAge(3600);
	}
}
