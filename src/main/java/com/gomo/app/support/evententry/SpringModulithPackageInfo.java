package com.gomo.app.support.evententry;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.PackageInfo;

@ApplicationModule(
	id = "support-evententry",
	displayName = "support-evententry",
	allowedDependencies = {
		"common-arch",
		"common-event",
		"common-util",
		"support-messagebroker::in"
	}
)
@PackageInfo
class SpringModulithPackageInfo {
}
