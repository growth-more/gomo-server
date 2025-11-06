package com.gomo.app.core.streak;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.PackageInfo;

@ApplicationModule(
	id = "core-streak",
	displayName = "core-streak",
	allowedDependencies = {
		"common-arch",
		"common-event",
		"common-exception",
		"common-jpa",
		"common-logging",
		"common-session",
		"common-util",
		"common-web",
		"support-evententry::in",
		"support-evententry::model",
	}
)
@PackageInfo
class SpringModulithPackageInfo {
}
