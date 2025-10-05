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
import org.springframework.http.ResponseEntity;
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
import com.gomo.app.support.auth.presentation.response.AccessTokenResponse;
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
	protected MemberApi memberApi;

	@Autowired
	protected GenerateJwtPortIn generateJwtPortIn;

	@Autowired
	protected AuthApi authApi;

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
		signup(sessionEmail, sessionPassword, sessionHandle);
		sessionInit(login(sessionEmail, sessionPassword));
		initMockHttpServletRequest(accessToken);
	}

	protected void signup(String email, String password, String handle) {
		String temporaryToken = generateJwtPortIn.generateTemporaryToken(email, 300);
		memberApi.create(CreateMemberRequest.of(email, password, handle, "testname", "testmotto", LoginProvider.EMAIL.name(), temporaryToken));
	}

	protected ResponseEntity<AccessTokenResponse> login(String email, String password) {
		return this.authApi.login(LoginRequest.of(email, password));
	}

	protected void sessionInit(ResponseEntity<AccessTokenResponse> responseEntity) {
		AccessTokenResponse responseBody = responseEntity.getBody();
		List<String> cookies = responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);
		this.sessionMemberId = responseBody.getPrincipalId();
		this.authInfo = AuthInfo.of(sessionMemberId);
		this.accessToken = responseBody.getAccessToken();
		this.refreshToken = extractTokenFromCookie(cookies);
	}

	private String extractTokenFromCookie(List<String> cookies) {
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

	public void initMockHttpServletRequest(String token) {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.addHeader("Authorization", "Bearer " + token);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
	}
}
