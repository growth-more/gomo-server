package com.gomo.app.interest.documentation;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.common.util.InterestRelationDataHelper;
import com.gomo.app.interest.common.util.MajorInterestDataHelper;
import com.gomo.app.interest.documentation.snippet.DeleteInterestSnippet;
import com.gomo.app.interest.domain.model.Interest;
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

@DisplayName("[Presentation documentation]: 관심사 삭제 테스트")
public class DeleteInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteInterestSnippet.create();

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest interest;

	@Autowired
	private InterestDataHelper interestDataHelper;

	@Autowired
	private MajorInterestDataHelper majorInterestDataHelper;

	@Autowired
	private InterestRelationDataHelper interestRelationDataHelper;

	@BeforeEach
	public void setUp() {
		interest = interestDataProvider.backend();
	}

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
		majorInterestDataHelper.cleanUp();
		interestRelationDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사를 삭제한다.")
	@Test
	void delete_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.when()
			.delete("/interests/{id}", interest.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
