package com.sporty.group.jackpotservice.service.reward;

import com.sporty.group.jackpotservice.model.Jackpot;
import com.sporty.group.jackpotservice.model.config.RewardType;

import java.math.BigDecimal;

public interface JackpotRewardChanceService {

    BigDecimal calculateFractionChance(Jackpot jackpot);

    RewardType getRewardType();
}
