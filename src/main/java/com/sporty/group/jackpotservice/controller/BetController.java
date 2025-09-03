package com.sporty.group.jackpotservice.controller;

import com.sporty.group.jackpotservice.dto.BetDto;
import com.sporty.group.jackpotservice.dto.JackpotRewardDto;
import com.sporty.group.jackpotservice.model.Bet;
import com.sporty.group.jackpotservice.model.JackpotReward;
import com.sporty.group.jackpotservice.service.BetService;
import com.sporty.group.jackpotservice.service.reward.JackpotRewardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bets")
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;
    private final JackpotRewardService jackpotRewardService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void publish(@Valid @RequestBody BetDto betDto) {
        Bet bet = Bet.from(betDto);

        betService.publishBet(bet);
    }

    @GetMapping("/{betId}/rewards")
    public Set<JackpotRewardDto> getJackpotRewardByBetId(@PathVariable String betId) {
        Set<JackpotReward> jackpotRewards = jackpotRewardService.findAllByBetId(betId);

        return jackpotRewards.stream()
                .map(JackpotRewardDto::fromJackpotReward)
                .collect(Collectors.toSet());
    }
}
