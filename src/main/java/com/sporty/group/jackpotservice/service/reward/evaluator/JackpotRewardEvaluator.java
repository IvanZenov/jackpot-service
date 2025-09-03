package com.sporty.group.jackpotservice.service.reward.evaluator;

import com.sporty.group.jackpotservice.model.Jackpot;

public interface JackpotRewardEvaluator {

    JackpotRewardEvaluationResult evaluate(Jackpot jackpot);
}
