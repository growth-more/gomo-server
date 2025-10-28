package com.gomo.app.core.interest.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.interest.domain.exception.ProficiencyAdjustFailureException;
import com.gomo.app.core.interest.domain.exception.code.ProficiencyErrorCode;

@ValueObject
public class ProficiencyCalculator {

	private final List<CalculatedLevelPolicy> policies;

	private ProficiencyCalculator(List<LevelThresholdPolicy> levelThresholdPolicies) {
		this.policies = convertToCalculatedPolicies(levelThresholdPolicies);
	}

	public static ProficiencyCalculator from(List<LevelThresholdPolicy> levelThresholdPolicies) {
		return new ProficiencyCalculator(levelThresholdPolicies);
	}

	/**
	 * Converts a list of {@link LevelThresholdPolicy} objects into
	 * {@link CalculatedLevelPolicy} objects with cumulative scores.
	 * <p>
	 * The final policy (e.g., level 100 → 10,000) is not included
	 * in the resulting list of calculated policies.
	 * As a result, the highest level does not advance further,
	 * but scores can continue to accumulate beyond that point.
	 * <p>
	 * Example:
	 * <pre>
	 * (98, 220),
	 * (99, 220),
	 * (100, 10000)
	 *
	 * → cumulativeScore progression:
	 * level 98 →  0
	 * level 99 → 220
	 * level 100 → 440  (policy for 100 is not included; scores keep accumulating)
	 * </pre>
	 *
	 * @param levelThresholdPolicies the ordered list of threshold policies
	 * @return a list of calculated level policies with cumulative scores
	 */
	private List<CalculatedLevelPolicy> convertToCalculatedPolicies(List<LevelThresholdPolicy> levelThresholdPolicies) {
		List<CalculatedLevelPolicy> calculatedPolicies = new ArrayList<>();
		int cumulativeScore = 0;

		for (LevelThresholdPolicy policy : levelThresholdPolicies) {
			calculatedPolicies.add(new CalculatedLevelPolicy(policy, cumulativeScore));
			cumulativeScore += policy.thresholdScore();
		}
		return calculatedPolicies;
	}

	/**
	 * Calculates and returns a {@link Proficiency} object based on the given total score.
	 *
	 * @param totalScore the total score used as the basis for calculation
	 * @return the calculated {@link Proficiency} object
	 * @throws ProficiencyAdjustFailureException if the total score is negative
	 * @throws IllegalStateException if no matching policy is found for the given total score
	 */
	public Proficiency calculate(int totalScore) {
		if (totalScore < 0) {
			throw new ProficiencyAdjustFailureException(ProficiencyErrorCode.NEGATIVE_TOTAL_SCORE);
		}

		for (int i = policies.size() - 1; i >= 0; i--) {
			CalculatedLevelPolicy currentPolicy = policies.get(i);

			if (totalScore >= currentPolicy.cumulativeScore()) {
				return new Proficiency(
					Level.of(currentPolicy.getLevel(), currentPolicy.getThreshold()),
					Score.of(totalScore - currentPolicy.cumulativeScore()),
					totalScore
				);
			}
		}

		throw new IllegalStateException("No applicable level policy found for the given total score: " + totalScore);
	}

	/**
	 * Represents a calculated policy that includes both the level threshold policy
	 * and the cumulative score required to reach that level.
	 * <p>
	 * The {@code cumulativeScore} represents the total score accumulated
	 * up to (but not including) the current level.
	 * For example, if each level requires 30 points:
	 * <ul>
	 *     <li>Level 0 → cumulativeScore = 0</li>
	 *     <li>Level 1 → cumulativeScore = 30</li>
	 *     <li>Level 2 → cumulativeScore = 60</li>
	 * </ul>
	 */
	private record CalculatedLevelPolicy(LevelThresholdPolicy policy, int cumulativeScore) {

		public int getLevel() {
			return policy.level();
		}

		public int getThreshold() {
			return policy.thresholdScore();
		}
	}
}
