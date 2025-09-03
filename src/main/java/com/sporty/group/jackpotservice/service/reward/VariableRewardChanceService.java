package com.sporty.group.jackpotservice.service.reward;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.group.jackpotservice.model.Jackpot;
import com.sporty.group.jackpotservice.model.config.RewardType;
import com.sporty.group.jackpotservice.model.config.VariableRewardConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class VariableRewardChanceService implements JackpotRewardChanceService {

    private final ObjectMapper objectMapper;

    @Override
    public BigDecimal calculateFractionChance(@NonNull Jackpot jackpot) {
        VariableRewardConfig config = parseConfig(jackpot.getRewardConfig().getConfigJson());

        BigDecimal growthSinceStart = jackpot.getGrowthSinceStart();

        return calculateChancePercent(config, growthSinceStart);
    }

    @Override
    public RewardType getRewardType() {
        return RewardType.VARIABLE;
    }

    @SneakyThrows
    private VariableRewardConfig parseConfig(String configJson) {
        return objectMapper.readValue(configJson, VariableRewardConfig.class);
    }

    /**
     * Calculates the chance percentage based on jackpot growth.
     * Uses growth ratio and linear interpolation.
     */
    // TODO: FRACTIONS
    private BigDecimal calculateChancePercent(VariableRewardConfig config, BigDecimal growth) {
        // If jackpot growth already reached or exceeded threshold => max chance
        if (growth.compareTo(config.growthThreshold()) >= 0) {
            return config.maxChancePercent();
        }

        BigDecimal ratio = calculateGrowthRatio(growth, config.growthThreshold());

        // 2. Interpolate between base chance and max chance using ratio
        return interpolateFractionChance(config.baseChancePercent(), config.maxChancePercent(), ratio);
    }

    /**
     * Calculates growth ratio between 0.0 and 1.0.
     */
    private BigDecimal calculateGrowthRatio(BigDecimal growthSinceStart, BigDecimal threshold) {
        if (threshold.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Growth threshold must be greater than zero");
        }
        return growthSinceStart.divide(threshold, 2, RoundingMode.HALF_UP);
    }

    /**
     * Linearly interpolates chance between base and max using ratio.
     * Example: base=10, max=50, ratio=0.5 => result=30.
     */
    private BigDecimal interpolateFractionChance(BigDecimal baseChance, BigDecimal maxChance, BigDecimal ratio) {
        BigDecimal difference = maxChance.subtract(baseChance);
        BigDecimal scaledDifference = difference.multiply(ratio);

        BigDecimal result = baseChance.add(scaledDifference);

        // Convert percentage to fraction
        return result.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
