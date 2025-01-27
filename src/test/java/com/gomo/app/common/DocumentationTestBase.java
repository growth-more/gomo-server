package com.gomo.app.common;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.config.RestAssureConfig;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = DatabaseContainerInitializer.class)
public abstract class DocumentationTestBase {

	@LocalServerPort
	protected int port;

	protected RequestSpecification specification;
	protected ObjectMapper objectMapper;

	protected DocumentationTestBase() {
		this.objectMapper = RestAssureConfig.initObjectMapper();
	}

	@BeforeEach
	void setUp(RestDocumentationContextProvider restDocumentation) {
		this.specification = new RequestSpecBuilder()
			.setPort(port)
			.addFilter(
				documentationConfiguration(restDocumentation)
					.operationPreprocessors()
			)
			.build();
	}
}
