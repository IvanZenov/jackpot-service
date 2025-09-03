package com.sporty.group.jackpotservice.model;

import com.sporty.group.jackpotservice.dto.BetDto;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

@Builder
public record Bet(
        @NonNull String betId,
        @NonNull String userId,
        @NonNull Long jackpotId,
        @NonNull BigDecimal betAmount) {

    public static Bet from(@NonNull BetDto betDto) {
        return Bet.builder()
                .betId(betDto.betId())
                .userId(betDto.userId())
                .jackpotId(betDto.jackpotId())
                .betAmount(betDto.betAmount())
                .build();
    }
}
