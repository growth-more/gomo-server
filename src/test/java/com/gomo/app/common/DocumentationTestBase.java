package com.gomo.app.common;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import java.util.List;
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
import com.gomo.app.auth.presentation.AuthMemberApi;
import com.gomo.app.auth.presentation.request.LoginMemberRequest;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.config.RestAssureConfig;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.MemberApi;
import com.gomo.app.member.presentation.request.CreateMemberRequest;
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
	AuthMemberApi authMemberApi;

	@Autowired
	private MemberRepository memberRepository;

	protected AuthInfo authInfo;
	protected UUID sessionMemberId;
	protected String accessToken;
	protected String refreshToken;
	protected String sessionEmail;
	protected String sessionHandle;

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

		sessionEmail = "testmember@naver.com";
		sessionHandle = "@Test";
		memberApi.create(CreateMemberRequest.of(sessionEmail, "Test1234@", sessionHandle, "testname", "testmotto", LoginProvider.EMAIL));
		var tokenResponse = this.authMemberApi.login(LoginMemberRequest.of(sessionEmail, "Test1234@"));
		this.sessionMemberId = tokenResponse.getBody().getMemberId();
		this.authInfo = AuthInfo.of(sessionMemberId);
		this.accessToken = tokenResponse.getBody().getToken().toString();
		this.refreshToken = extractTokenFromCookie(tokenResponse.getHeaders().get(HttpHeaders.SET_COOKIE));
		initMockHttpServletRequest(accessToken);
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
