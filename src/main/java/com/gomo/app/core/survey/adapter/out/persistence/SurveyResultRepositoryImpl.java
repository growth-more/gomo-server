package com.gomo.app.core.survey.adapter.out.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gomo.app.common.util.UUIDConverter;
import com.gomo.app.core.survey.domain.model.SurveyResult;
import com.gomo.app.core.survey.domain.repository.SurveyResultRepository;

@Repository
public class SurveyResultRepositoryImpl implements SurveyResultRepository {

	private final JdbcTemplate jdbcTemplate;

	public SurveyResultRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void saveAll(List<SurveyResult> surveyResults) {
		final String sql = "INSERT INTO survey_result (respondent_id, survey_question_id, survey_item_id, survey_item_content, custom_answer) VALUES (?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public int getBatchSize() {
				return surveyResults.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SurveyResult surveyResult = surveyResults.get(i);

				ps.setBytes(1, UUIDConverter.uuidToBytes(surveyResult.getRespondentId()));
				ps.setBytes(2, UUIDConverter.uuidToBytes(surveyResult.getSurveyQuestionId()));
				ps.setBytes(3, UUIDConverter.uuidToBytes(surveyResult.getSurveyItemId()));
				ps.setObject(4, surveyResult.getSurveyItemContent());
				ps.setString(5, surveyResult.getCustomAnswer());
			}
		});
	}

	@Override
	public List<SurveyResult> findAllByRespondentId(UUID targetId) {
		final String sql = "SELECT respondent_id, survey_question_id, survey_item_id, survey_item_content, custom_answer " +
			"FROM survey_result WHERE respondent_id = ?";

		return jdbcTemplate.query(sql, new Object[] {UUIDConverter.uuidToBytes(targetId)}, (rs, rowNum) -> {
			UUID respondentId = UUIDConverter.bytesToUuid(rs.getBytes("respondent_id"));
			UUID surveyQuestionId = UUIDConverter.bytesToUuid(rs.getBytes("survey_question_id"));
			UUID surveyItemId = UUIDConverter.bytesToUuid(rs.getBytes("survey_item_id"));

			return SurveyResult.of(
				respondentId,
				surveyQuestionId,
				surveyItemId,
				rs.getString("survey_item_content"),
				rs.getString("custom_answer")
			);
		});
	}
}
