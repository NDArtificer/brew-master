package com.artificer.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableWebMvc
@EnableCaching
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").setCachePeriod(3600)
				.resourceChain(true).addResolver(new PathResourceResolver());
	}

	@Bean
	public CacheManager cacheManager() {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(3).expireAfterAccess(20,
				TimeUnit.SECONDS);
		GuavaCacheManager guavaCacheManager = new GuavaCacheManager();

		guavaCacheManager.setCacheBuilder(cacheBuilder);
		return guavaCacheManager;

	}

}
