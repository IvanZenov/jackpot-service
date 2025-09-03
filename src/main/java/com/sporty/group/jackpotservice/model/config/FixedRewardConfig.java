package com.sporty.group.jackpotservice.model.config;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record FixedRewardConfig(
        // Represented as % like 5%
        @NonNull BigDecimal chancePercent) {

    public BigDecimal fractionChancePercent() {
        return this.chancePercent.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
