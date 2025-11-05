package com.gomo.app.modulith;

import java.util.stream.Stream;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.core.ApplicationModuleDetectionStrategy;
import org.springframework.modulith.core.JavaPackage;

public class AnnotationBasedModuleDetectionStrategy implements ApplicationModuleDetectionStrategy {

	@Override
	public Stream<JavaPackage> getModuleBasePackages(JavaPackage basePackage) {
		return basePackage.getSubPackagesAnnotatedWith(ApplicationModule.class);
	}
}
