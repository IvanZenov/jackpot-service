package com.sporty.group.jackpotservice.service.contribution;

import com.sporty.group.jackpotservice.model.Jackpot;
import com.sporty.group.jackpotservice.model.config.ContributionType;

import java.math.BigDecimal;

public interface JackpotContributionCalculator {
    BigDecimal calculate(Jackpot jackpot, BigDecimal betAmount);

    ContributionType getContributionType();
}
