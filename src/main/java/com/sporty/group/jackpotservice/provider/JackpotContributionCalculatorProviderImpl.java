package com.sporty.group.jackpotservice.provider;

import com.sporty.group.jackpotservice.model.config.ContributionType;
import com.sporty.group.jackpotservice.service.contribution.JackpotContributionCalculator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JackpotContributionCalculatorProviderImpl implements JackpotContributionCalculatorProvider {

    private final Map<ContributionType, JackpotContributionCalculator> contributionTypeToContributionService;

    public JackpotContributionCalculatorProviderImpl(List<JackpotContributionCalculator> jackpotContributionCalculators) {
        this.contributionTypeToContributionService = jackpotContributionCalculators.stream()
                .collect(Collectors.toMap(JackpotContributionCalculator::getContributionType, Function.identity()));
    }

    @Override
    public JackpotContributionCalculator provide(@NonNull ContributionType contributionType) {
        return Optional.ofNullable(contributionTypeToContributionService.get(contributionType))
                .orElseThrow(() -> {
                    log.error("Unsupported contribution type: {}", contributionType);
                    return new IllegalArgumentException("Unsupported contribution type: %s".formatted(contributionType));
                });
    }
}
