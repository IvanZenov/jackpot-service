package com.sporty.group.jackpotservice.model.config;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record FixedContributionConfig(
        // Represented % like 5%
        @NonNull BigDecimal percent) {

    public BigDecimal fractionPercentAsBigDecimal() {
        return this.percent.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
