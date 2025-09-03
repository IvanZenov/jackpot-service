package com.sporty.group.jackpotservice.model.config;

import lombok.NonNull;

import java.math.BigDecimal;

public record VariableContributionConfig(
        // Represented as % like 5%
        @NonNull BigDecimal percentInitial,
        @NonNull BigDecimal percentMin,
        @NonNull BigDecimal decreasePerStep,
        @NonNull BigDecimal growthInterval) {
}
