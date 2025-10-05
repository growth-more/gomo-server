package com.gomo.app.common;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.config.RestAssureConfig;
import com.gomo.app.common.jwt.port.GenerateJwtPortIn;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.MemberId;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.presentation.MemberApi;
import com.gomo.app.core.member.presentation.request.CreateMemberRequest;
import com.gomo.app.support.auth.presentation.api.AuthApi;
import com.gomo.app.support.auth.presentation.request.LoginRequest;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.google.common.net.HttpHeaders;

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

	@Autowired
	MemberApi memberApi;

	@Autowired
	protected GenerateJwtPortIn generateJwtPortIn;

	@Autowired
	AuthApi authApi;

	@Autowired
	private MemberRepository memberRepository;

	protected AuthInfo authInfo;
	protected UUID sessionMemberId;
	protected String accessToken;
	protected String refreshToken;
	protected String sessionEmail;
	protected String sessionPassword;
	protected String sessionHandle;

	protected DocumentationTestBase() {
		this.objectMapper = RestAssureConfig.initObjectMapper();
	}

	@BeforeEach
	void setUp(RestDocumentationContextProvider restDocumentation) {
		this.specification = new RequestSpecBuilder()
			.setPort(port)
			.addFilter(documentationConfiguration(restDocumentation).operationPreprocessors())
			.build();

		sessionEmail = "testmember@naver.com";
		sessionPassword = "Test1234@";
		sessionHandle = "@Test";
		signup();
		login();
		initMockHttpServletRequest(accessToken);
	}

	private void login() {
		var tokenResponse = this.authApi.login(LoginRequest.of(sessionEmail, sessionPassword));
		this.sessionMemberId = Objects.requireNonNull(tokenResponse.getBody()).getPrincipalId();
		this.authInfo = AuthInfo.of(sessionMemberId);
		this.accessToken = tokenResponse.getBody().getAccessToken();
		this.refreshToken = extractTokenFromCookie(tokenResponse.getHeaders().get(HttpHeaders.SET_COOKIE));
	}

	private void signup() {
		String temporaryToken = generateJwtPortIn.generateTemporaryToken(sessionEmail, 300);
		memberApi.create(CreateMemberRequest.of(sessionEmail, sessionPassword, sessionHandle, "testname", "testmotto", LoginProvider.EMAIL.name(), temporaryToken));
	}

	String extractTokenFromCookie(List<String> cookies) {
		if (cookies != null) {
			for (String cookie : cookies) {
				if (cookie.contains("refreshToken=")) {
					int start = cookie.indexOf("refreshToken=") + "refreshToken=".length();
					int end = cookie.indexOf(";", start);
					return (end > start) ? cookie.substring(start, end) : cookie.substring(start);
				}
			}
		}
		return null;
	}

	@AfterEach
	void tearDown() {
		memberRepository.deleteById(MemberId.of(sessionMemberId));
	}

	public static void initMockHttpServletRequest(String token) {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.addHeader("Authorization", "Bearer " + token);

		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
	}
}
