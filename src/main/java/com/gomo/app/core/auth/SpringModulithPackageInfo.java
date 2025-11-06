package com.gomo.app.core.auth;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.PackageInfo;

@ApplicationModule(
	id = "core-auth",
	displayName = "core-auth",
	allowedDependencies = {
		"common-arch",
		"common-exception",
		"common-logging",
		"common-session",
		"core-member::in",
		"core-member::model",
		"core-member::command",
		"support-logging"
	}
)
@PackageInfo
class SpringModulithPackageInfo {
}
