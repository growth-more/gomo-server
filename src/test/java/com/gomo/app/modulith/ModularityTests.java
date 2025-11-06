package com.gomo.app.modulith;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import com.gomo.app.GomoApplication;

/**
 * Tests to verify the modular structure and generate documentation for the modules.
 */
public class ModularityTests {

	ApplicationModules modules = ApplicationModules.of(GomoApplication.class);

	@DisplayName("모듈간 순환 의존성은 존재하지 않는다.")
	@Test
	void verifies_modular_structure() {
		modules.verify();
	}

	@DisplayName("모듈 문서를 생성한다.")
	@Test
	void create_module_documentation() {
		new Documenter(modules)
			.writeModulesAsPlantUml()
			.writeIndividualModulesAsPlantUml()
			.writeModuleCanvases();
	}
}
