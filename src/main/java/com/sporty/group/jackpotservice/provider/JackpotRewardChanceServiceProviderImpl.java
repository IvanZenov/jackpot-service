package com.sporty.group.jackpotservice.provider;

import com.sporty.group.jackpotservice.model.config.RewardType;
import com.sporty.group.jackpotservice.service.reward.JackpotRewardChanceService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JackpotRewardChanceServiceProviderImpl implements JackpotRewardChanceServiceProvider {

    private final Map<RewardType, JackpotRewardChanceService> rewardTypeToJackpotRewardService;

    public JackpotRewardChanceServiceProviderImpl(List<JackpotRewardChanceService> jackpotRewardChanceServices) {
        this.rewardTypeToJackpotRewardService = jackpotRewardChanceServices.stream()
                .collect(Collectors.toMap(JackpotRewardChanceService::getRewardType, Function.identity()));
    }

    @Override
    public JackpotRewardChanceService provide(@NonNull RewardType rewardType) {
        return Optional.ofNullable(rewardTypeToJackpotRewardService.get(rewardType))
                .orElseThrow(() -> {
                    log.error("Unsupported reward type: {}", rewardType);
                    return new IllegalArgumentException("Unsupported reward type: %s".formatted(rewardType));
                });
    }
}
