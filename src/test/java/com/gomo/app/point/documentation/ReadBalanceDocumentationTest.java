package com.gomo.app.point.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.point.common.dataprovider.PointWalletDataProvider;
import com.gomo.app.point.documentation.snippet.ReadBalanceSnippet;
import com.gomo.app.point.domain.model.PointWallet;

@DisplayName("[Presentation documentation]: 포인트 잔고 조회 테스트")
public class ReadBalanceDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ReadBalanceSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private PointWalletDataProvider pointWalletDataProvider;
	private PointWallet pointWallet;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		pointWallet = pointWalletDataProvider.pointWallet();
	}

	@DisplayName("사용자가 포인트 잔고를 조회한다.")
	@Test
	void history_point() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.when()
			.get("/points/balances")
			.then()
			.statusCode(OK.value())
			.body("amount", equalTo(pointWallet.getBalance().getAmount()));
	}
}
