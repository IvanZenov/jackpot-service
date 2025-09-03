package com.sporty.group.jackpotservice.service.contribution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.group.jackpotservice.model.Jackpot;
import com.sporty.group.jackpotservice.model.config.ContributionType;
import com.sporty.group.jackpotservice.model.config.VariableContributionConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class VariableJackpotContributionCalculator implements JackpotContributionCalculator {

    private final ObjectMapper objectMapper;

    @Override
    public BigDecimal calculate(@NonNull Jackpot jackpot, @NonNull BigDecimal betAmount) {
        VariableContributionConfig config = parseConfig(jackpot.getContributionConfig().getConfigJson());
        BigDecimal growthSinceStart = jackpot.getGrowthSinceStart();

        BigDecimal contributionFractionPercent = getContributionFractionPercent(growthSinceStart, config);

        return betAmount.multiply(contributionFractionPercent);
    }

    @Override
    public ContributionType getContributionType() {
        return ContributionType.VARIABLE;
    }

    private BigDecimal getContributionFractionPercent(BigDecimal growthSinceStart, VariableContributionConfig config) {
        // How much the jackpot must increase before contribution decreases
        BigDecimal growthInterval = config.growthInterval();

        BigDecimal growthSteps = growthSinceStart.divide(growthInterval, 0, RoundingMode.DOWN);

        // Contribution percentage = initial % - (steps × decrease per step)
        BigDecimal contributionPercent = config.percentInitial().subtract(growthSteps.multiply(config.decreasePerStep()));

        if (contributionPercent.compareTo(config.percentMin()) < 0) {
            contributionPercent = config.percentMin();
        }

        return contributionPercent.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @SneakyThrows
    private VariableContributionConfig parseConfig(String configJson) {
        return objectMapper.readValue(configJson, VariableContributionConfig.class);
    }
}
