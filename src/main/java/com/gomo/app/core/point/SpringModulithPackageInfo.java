package com.gomo.app.core.point;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.PackageInfo;

@ApplicationModule(
	id = "core-point",
	displayName = "core-point",
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
