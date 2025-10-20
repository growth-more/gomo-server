package com.gomo.app.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource.core")
public class CoreDBProperties {

	private String url;
	private String driverClassName;
	private String username;
	private String password;
}
