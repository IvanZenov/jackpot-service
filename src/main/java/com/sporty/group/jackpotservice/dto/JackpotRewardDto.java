package com.sporty.group.jackpotservice.dto;

import com.sporty.group.jackpotservice.model.JackpotReward;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record JackpotRewardDto(
        @NotNull Long id,
        @NotBlank String betId,
        @NotBlank String userId,
        @NotNull Long jackpotId,
        @NotNull BigDecimal jackpotRewardAmount,
        @NotNull Instant createdAt) {

    public static JackpotRewardDto fromJackpotReward(@NonNull JackpotReward reward) {
        return JackpotRewardDto.builder()
                .id(reward.getId())
                .betId(reward.getBetId())
                .userId(reward.getUserId())
                .jackpotId(reward.getJackpotId())
                .jackpotRewardAmount(reward.getJackpotRewardAmount())
                .createdAt(reward.getCreatedAt())
                .build();
    }
}
