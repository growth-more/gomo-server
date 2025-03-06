package com.gomo.app.common;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import com.gomo.app.member.presentation.LoginMemberApi;
import com.gomo.app.member.presentation.request.LoginMemberRequest;
import com.gomo.app.member.presentation.response.TokenResponse;
import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.config.RestAssureConfig;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.testcontainers.shaded.org.yaml.snakeyaml.tokens.Token;

import java.util.List;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = DatabaseContainerInitializer.class)
public abstract class DocumentationTestBase {

	@LocalServerPort
	protected int port;

	protected RequestSpecification specification;
	protected ObjectMapper objectMapper;

	@Autowired
	LoginMemberApi loginMemberApi;
	protected String accessToken;
	protected String refreshToken;

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
		ResponseEntity<TokenResponse> response = this.loginMemberApi.login(LoginMemberRequest.of("gomotest@naver.com", "Gomotest1234@"));
		this.accessToken = response.getBody().getToken().toString();
		this.refreshToken = extractTokenFromCookie(response.getHeaders().get(HttpHeaders.SET_COOKIE));
		initMockHttpServletRequest(accessToken);
	}

	String extractTokenFromCookie(List<String> cookies){
		if(cookies != null){
			for (String cookie: cookies){
				if (cookie.contains("refreshToken=")){
					int start = cookie.indexOf("refreshToken=") + "refreshToken=".length();
            		int end = cookie.indexOf(";", start);
            		return (end > start) ? cookie.substring(start, end) : cookie.substring(start);
				}
			}
		}
		return null;
	}

	public static void initMockHttpServletRequest(String token) {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.addHeader("Authorization", "Bearer " + token);

		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
	}
}
