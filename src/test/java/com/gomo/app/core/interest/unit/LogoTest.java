package com.gomo.app.core.interest.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.interest.domain.model.Logo;

@DisplayName("[Domain unit]: 관심사 로고 테스트")
public class LogoTest {

	private static final String DEFAULT_IMAGE = "DEFAULT_IMAGE";
	private static final String UPLOAD_IMAGE = "https://mini-cloud/upload-image";

	@DisplayName("로고를 생성한다.")
	@Test
	void create_logo() {
		Logo logo = Logo.of(UPLOAD_IMAGE);

		assertThat(logo.getUrl()).isEqualTo(UPLOAD_IMAGE);
	}

	@DisplayName("사용자가 로고를 등록하지 않으면, 기본 이미지가 등록된다.")
	@Test
	void create_default_logo() {
		Logo logo = Logo.of(null);

		assertThat(logo.getUrl()).isEqualTo(DEFAULT_IMAGE);
	}

	@DisplayName("기본 로고인지 확인한다.")
	@Test
	void is_default_logo() {
		Logo logo = Logo.of(null);

		assertThat(logo.isDefault()).isTrue();
	}

	@DisplayName("사용자가 등록한 로고라면 기본 로고가 등록되지 않는다.")
	@Test
	void not_default_logo() {
		Logo logo = Logo.of(UPLOAD_IMAGE);

		assertThat(logo.isDefault()).isFalse();
	}

	@DisplayName("로고를 수정한다.")
	@Test
	void update_logo() {
		Logo logo = Logo.of(UPLOAD_IMAGE);
		String updatedLogoUrl = "https://mini-cloud/updated-image";
		Logo updatedLogo = logo.updateUrl(updatedLogoUrl);

		assertThat(updatedLogo.getUrl()).isEqualTo(updatedLogoUrl);
	}

	@DisplayName("로고를 삭제한다.")
	@Test
	void delete_logo() {
		Logo logo = Logo.of(UPLOAD_IMAGE);
		Logo deletedLogo = logo.delete();

		assertThat(deletedLogo.getUrl()).isEqualTo(DEFAULT_IMAGE);
	}
}
