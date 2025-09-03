package com.sporty.group.jackpotservice.service.reward;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.group.jackpotservice.model.Jackpot;
import com.sporty.group.jackpotservice.model.config.FixedRewardConfig;
import com.sporty.group.jackpotservice.model.config.RewardType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class FixedRewardChanceService implements JackpotRewardChanceService {

    private final ObjectMapper objectMapper;

    @Override
    public BigDecimal calculateFractionChance(@NonNull Jackpot jackpot) {
        FixedRewardConfig fixedRewardConfig = parseConfig(jackpot.getRewardConfig().getConfigJson());
        return fixedRewardConfig.fractionChancePercent();
    }

    @Override
    public RewardType getRewardType() {
        return RewardType.FIXED;
    }

    @SneakyThrows
    private FixedRewardConfig parseConfig(String configJson) {
        return objectMapper.readValue(configJson, FixedRewardConfig.class);
    }
}
