package com.gomo.app.support.image;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.PackageInfo;

@ApplicationModule(
	id = "support-image",
	displayName = "support-image",
	allowedDependencies = {
		"common-arch",
		"common-exception"
	}
)
@PackageInfo
class SpringModulithPackageInfo {
}
