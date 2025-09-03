package com.sporty.group.jackpotservice.provider;

import com.sporty.group.jackpotservice.model.config.ContributionType;
import com.sporty.group.jackpotservice.service.contribution.JackpotContributionCalculator;

public interface JackpotContributionCalculatorProvider {
    JackpotContributionCalculator provide(ContributionType contributionType);
}
