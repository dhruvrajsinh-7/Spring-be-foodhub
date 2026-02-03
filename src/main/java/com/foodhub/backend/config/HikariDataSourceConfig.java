package com.foodhub.backend.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
@Slf4j
public class HikariDataSourceConfig {

	@PostConstruct
	public void logHikariConfig() {
		log.info("HikariCP configuration auto-loaded from application.properties");
		log.info("HikariCP will use properties prefixed with 'spring.datasource.hikari.*'");
	}

}
