package com.sporty.group.jackpotservice.service.reward.evaluator;

import com.sporty.group.jackpotservice.model.Jackpot;
import com.sporty.group.jackpotservice.model.config.RewardType;
import com.sporty.group.jackpotservice.provider.JackpotRewardChanceServiceProvider;
import com.sporty.group.jackpotservice.service.reward.JackpotRewardChanceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class JackpotRewardEvaluatorImpl implements JackpotRewardEvaluator {

    private final JackpotRewardChanceServiceProvider jackpotRewardChanceServiceProvider;
    // TODO: move it to a service
    private final Random random = new Random();

    @Override
    public JackpotRewardEvaluationResult evaluate(@NonNull Jackpot jackpot) {
        RewardType rewardType = jackpot.getRewardConfig().getType();
        JackpotRewardChanceService jackpotRewardChanceService = jackpotRewardChanceServiceProvider.provide(rewardType);

        BigDecimal rewardFractionChance = jackpotRewardChanceService.calculateFractionChance(jackpot);
        BigDecimal roll = BigDecimal.valueOf(random.nextDouble());

        boolean isRewardWon = rewardFractionChance.compareTo(roll) >= 0;

        BigDecimal jackpotRewardAmount = isRewardWon ? jackpot.getCurrentPool() : BigDecimal.ZERO;

        return JackpotRewardEvaluationResult.builder()
                .isRewardWon(isRewardWon)
                .roll(roll)
                .jackpotRewardAmount(jackpotRewardAmount)
                .rewardChance(rewardFractionChance)
                .build();
    }
}
