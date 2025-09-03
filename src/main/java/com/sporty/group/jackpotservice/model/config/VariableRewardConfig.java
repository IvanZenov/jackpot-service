package com.sporty.group.jackpotservice.model.config;

import lombok.NonNull;

import java.math.BigDecimal;

public record VariableRewardConfig(
        // Represent as % like 5%
        @NonNull BigDecimal baseChancePercent,
        @NonNull BigDecimal maxChancePercent,
        @NonNull BigDecimal growthThreshold) {
}
