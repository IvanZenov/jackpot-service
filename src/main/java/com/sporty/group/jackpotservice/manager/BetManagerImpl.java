package com.sporty.group.jackpotservice.manager;

import com.sporty.group.jackpotservice.messaging.publisher.EventPublisher;
import com.sporty.group.jackpotservice.model.Bet;
import com.sporty.group.jackpotservice.model.Jackpot;
import com.sporty.group.jackpotservice.model.JackpotContribution;
import com.sporty.group.jackpotservice.model.JackpotReward;
import com.sporty.group.jackpotservice.provider.JackpotContributionCalculatorProvider;
import com.sporty.group.jackpotservice.repository.JackpotContributionRepository;
import com.sporty.group.jackpotservice.repository.JackpotRepository;
import com.sporty.group.jackpotservice.repository.JackpotRewardRepository;
import com.sporty.group.jackpotservice.service.contribution.JackpotContributionCalculator;
import com.sporty.group.jackpotservice.service.reward.evaluator.JackpotRewardEvaluationResult;
import com.sporty.group.jackpotservice.service.reward.evaluator.JackpotRewardEvaluator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BetManagerImpl implements BetManager {

    private final JackpotRepository jackpotRepository;
    private final JackpotContributionRepository jackpotContributionRepository;
    private final JackpotContributionCalculatorProvider jackpotContributionCalculatorProvider;
    private final JackpotRewardEvaluator jackpotRewardEvaluator;
    private final JackpotRewardRepository jackpotRewardRepository;

    @Override
    @Transactional
    public void processBet(@NonNull Bet bet) {
        log.debug("Evaluating bet={}", bet);

        Optional<Jackpot> jackpotOpt = jackpotRepository.findById(bet.jackpotId());
        if (jackpotOpt.isEmpty()) {
            log.warn("Jackpot with id {} was not found for bet={}", bet.jackpotId(), bet);
            return;
        }

        Jackpot jackpot = jackpotOpt.get();

        JackpotContributionCalculator jackpotContributionCalculator = jackpotContributionCalculatorProvider.provide(jackpot.getContributionConfig().getType());
        BigDecimal contributionAmount = jackpotContributionCalculator.calculate(jackpot, bet.betAmount());

        jackpot.increaseCurrentPool(contributionAmount);
        JackpotContribution jackpotContribution = mapToJackpotContribution(bet, contributionAmount, jackpot.getCurrentPool());

        JackpotRewardEvaluationResult jackpotRewardEvaluationResult = jackpotRewardEvaluator.evaluate(jackpot);
        jackpotContributionRepository.save(jackpotContribution);

        if (jackpotRewardEvaluationResult.isRewardWon()) {
            log.info("Bet with id {} was won jackpotId={}. [jackpotRewardEvaluationResult={}]", bet.betId(), jackpot.getId(), jackpotRewardEvaluationResult);

            jackpot.resetCurrentPoolToInitial();
            JackpotReward jackpotReward = mapToJackpotReward(bet, jackpotRewardEvaluationResult.jackpotRewardAmount());
            jackpotRewardRepository.save(jackpotReward);
        }

        jackpotRepository.save(jackpot);

        log.info("Bet contribution for jackpot={} and jackpotContribution={} successfully processed [bet={}]",
                jackpot.getId(), jackpotContribution.getId(), bet);
    }

    private JackpotReward mapToJackpotReward(Bet bet, BigDecimal jackpotRewardAmount) {
        return JackpotReward.builder()
                .betId(bet.betId())
                .jackpotId(bet.jackpotId())
                .userId(bet.userId())
                .jackpotRewardAmount(jackpotRewardAmount)
                .createdAt(Instant.now())
                .build();
    }

    private JackpotContribution mapToJackpotContribution(Bet bet, BigDecimal contributionAmount, BigDecimal currentPool) {
        return JackpotContribution.builder()
                .betId(bet.betId())
                .userId(bet.userId())
                .jackpotId(bet.jackpotId())
                .stakeAmount(bet.betAmount())
                .contributionAmount(contributionAmount)
                .currentJackpotAmount(currentPool)
                .createdAt(Instant.now())
                .build();
    }
}
