package com.gomo.app.core.member;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.PackageInfo;

@ApplicationModule(
	id = "core-member",
	displayName = "core-member",
	allowedDependencies = {
		"common-arch",
		"common-exception",
		"common-jpa",
		"common-logging",
		"common-session",
		"common-util",
		"core-point::in",
		"core-streak::in",
		"support-logging",
		"support-image::in"
	}
)
@PackageInfo
class SpringModulithPackageInfo {
}
