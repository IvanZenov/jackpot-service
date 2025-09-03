package com.sporty.group.jackpotservice.service.reward;

import com.sporty.group.jackpotservice.model.JackpotReward;

import java.util.Set;

public interface JackpotRewardService {
    Set<JackpotReward> findAllByBetId(String betId);
}
