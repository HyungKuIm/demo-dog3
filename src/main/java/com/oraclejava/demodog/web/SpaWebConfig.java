package com.oraclejava.demodog.web;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * React(BrowserRouter) SPA 지원.
 * static/ 에 실제 파일이 있으면 그 파일을, 없으면 index.html 을 돌려줘서
 * /dog/add, /dog/update/1 같은 경로를 새로고침해도 404가 나지 않게 한다.
 * 컨트롤러(/api/**, /actuator/**)가 먼저 매칭되므로 API에는 영향이 없다.
 */
@Configuration
public class SpaWebConfig implements WebMvcConfigurer {

	private static final Resource INDEX = new ClassPathResource("static/index.html");

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:/static/")
			.resourceChain(true)
			.addResolver(new PathResourceResolver() {
				@Override
				protected Resource getResource(String resourcePath, Resource location) throws IOException {
					Resource requested = location.createRelative(resourcePath);
					if (requested.exists() && requested.isReadable()) {
						return requested;
					}
					// 없는 API 경로나 파일(.js, .png 등)은 index.html 대신 정상적으로 404
					if (resourcePath.startsWith("api/") || resourcePath.startsWith("actuator/")
							|| resourcePath.contains(".")) {
						return null;
					}
					return INDEX.exists() ? INDEX : null;
				}
			});
	}

}
