package com.sporty.group.jackpotservice.service.reward.evaluator;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

@Builder
public record JackpotRewardEvaluationResult(
        @NonNull BigDecimal jackpotRewardAmount,
        @NonNull BigDecimal rewardChance,
        @NonNull BigDecimal roll,
        boolean isRewardWon) {
}
