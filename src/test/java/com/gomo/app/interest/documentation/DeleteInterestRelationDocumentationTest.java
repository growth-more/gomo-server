package com.gomo.app.interest.documentation;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestRelationDataProvider;
import com.gomo.app.interest.common.util.InterestRelationDataHelper;
import com.gomo.app.interest.documentation.snippet.DeleteInterestRelationSnippet;
import com.gomo.app.interest.domain.model.InterestRelation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("[Presentation documentation]: 관심사 관계 삭제 테스트")
public class DeleteInterestRelationDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteInterestRelationSnippet.create();

	@Autowired
	private InterestRelationDataHelper interestRelationDataHelper;

	@Autowired
	private InterestRelationDataProvider interestRelationDataProvider;
	private InterestRelation backendToJava;

	@BeforeEach
	public void setUp() {
		backendToJava = interestRelationDataProvider.backendToJava();
	}

	@AfterEach
	public void tearDown() {
		interestRelationDataHelper.cleanUp();
	}

	@DisplayName("사용자가 두 가지 관심사 간의 연결선을 삭제한다.")
	@Test
	void delete_interest_relation() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.delete("/interests/networks/relations/{id}", backendToJava.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
