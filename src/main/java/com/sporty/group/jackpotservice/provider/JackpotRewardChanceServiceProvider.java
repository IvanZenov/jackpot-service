package com.sporty.group.jackpotservice.provider;

import com.sporty.group.jackpotservice.model.config.RewardType;
import com.sporty.group.jackpotservice.service.reward.JackpotRewardChanceService;

public interface JackpotRewardChanceServiceProvider {
    JackpotRewardChanceService provide(RewardType rewardType);
}
