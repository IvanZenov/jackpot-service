package com.sporty.group.jackpotservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BetDto(
        @NotBlank
        String betId,

        @NotBlank
        String userId,

        @NotNull
        Long jackpotId,

        @NotNull
        @Min(0)
        BigDecimal betAmount) {
}
