package com.gomo.app.core.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.SubjectName;

@DisplayName("[Domain unit]: 주제 이름 생성 및 수정 테스트")
public class SubjectNameTest {

	@DisplayName("주제 이름을 생성한다.")
	@Test
	void create_subject_name() {
		SubjectName subjectName = SubjectName.of("subject name");

		assertThat(subjectName.toString()).isEqualTo("subject name");
	}

	@DisplayName("주제 이름을 수정한다.")
	@Test
	void update_subject_name() {
		SubjectName subjectName = SubjectName.of("subject name");
		SubjectName updatedSubjectName = subjectName.update("updated subject name");

		assertThat(updatedSubjectName.toString()).isEqualTo("updated subject name");
	}
}
