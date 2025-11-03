package com.gomo.app.test;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.auth.adapter.in.api.AuthApi;
import com.gomo.app.core.auth.adapter.in.api.request.CreatePrincipalRequest;
import com.gomo.app.core.auth.adapter.in.api.request.LoginRequest;
import com.gomo.app.core.auth.adapter.in.api.response.AccessTokenResponse;
import com.gomo.app.core.auth.adapter.in.security.AuthInfo;
import com.gomo.app.core.auth.application.port.out.JwtCreator;
import com.google.common.net.HttpHeaders;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

@ExtendWith(RestDocumentationExtension.class)
@IntegrationTest
public abstract class DocumentationTestBase {

	@LocalServerPort
	protected int port;

	protected RequestSpecification specification;

	@Autowired
	protected JwtCreator jwtCreator;

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

	@AfterEach
	void tearDown() {
		memberRepository.deleteById(sessionMemberId);
	}

	protected void signup(String email, String password, String handle) {
		String temporaryToken = jwtCreator.createTemporaryToken(email, 300);
		authApi.signup(CreatePrincipalRequest.of(email, password, handle, "testname", "testmotto", LoginProvider.EMAIL.name(), temporaryToken));
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

	private void initMockHttpServletRequest(String token) {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.addHeader("Authorization", "Bearer " + token);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
	}
}
