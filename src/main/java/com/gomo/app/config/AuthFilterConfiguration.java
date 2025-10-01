package com.gomo.app.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthFilterConfiguration {

	private List<ExcludedPath> excludedPaths;

	public boolean isExcluded(String requestURI, String requestMethod) {
		return excludedPaths.stream().anyMatch(path -> requestURI.startsWith(path.getPath()) && path.getMethods().contains(requestMethod));
	}

	@Getter
	@Setter
	public static class ExcludedPath {
		private String path;
		private List<String> methods;
	}
}
