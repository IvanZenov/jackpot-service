package com.sporty.group.jackpotservice.service.contribution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.group.jackpotservice.model.Jackpot;
import com.sporty.group.jackpotservice.model.config.ContributionType;
import com.sporty.group.jackpotservice.model.config.FixedContributionConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FixedJackpotContributionCalculator implements JackpotContributionCalculator {

    private final ObjectMapper objectMapper;

    @Override
    public BigDecimal calculate(@NonNull Jackpot jackpot, @NonNull BigDecimal betAmount) {
        FixedContributionConfig config = parseConfig(jackpot.getContributionConfig().getConfigJson());

        return betAmount.multiply(config.fractionPercentAsBigDecimal());
    }

    @Override
    public ContributionType getContributionType() {
        return ContributionType.FIXED;
    }

    @SneakyThrows
    private FixedContributionConfig parseConfig(String configJson) {
        return objectMapper.readValue(configJson, FixedContributionConfig.class);
    }
}
