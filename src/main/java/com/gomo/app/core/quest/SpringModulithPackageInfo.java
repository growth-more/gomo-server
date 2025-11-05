package com.gomo.app.core.quest;

import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.PackageInfo;

@ApplicationModule(
	id = "core-quest",
	displayName = "core-quest",
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
		"core-interest::in",
		"core-interest::dto",
		"support-llm::in",
		"support-evententry::in",
		"support-messagebroker::in",
		"support-messagebroker::model",
	}
)
@PackageInfo
class SpringModulithPackageInfo {
}
