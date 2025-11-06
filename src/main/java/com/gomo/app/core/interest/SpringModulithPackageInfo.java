package com.gomo.app.core.interest;

import org.springframework.modulith.ApplicationModule;

@ApplicationModule(
	id = "core-interest",
	displayName = "core-interest",
	allowedDependencies = {
		"common-arch",
		"common-displayorder",
		"common-event",
		"common-exception",
		"common-jpa",
		"common-logging",
		"common-session",
		"common-util",
		"core-member::in",
		"core-member::dto",
		"support-evententry::in",
		"support-evententry::model",
		"support-image::in"
	}
)
@org.springframework.modulith.PackageInfo
class SpringModulithPackageInfo {
}
