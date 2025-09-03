package com.sporty.group.jackpotservice.service;

import com.sporty.group.jackpotservice.exception.JackpotNotFoundException;
import com.sporty.group.jackpotservice.messaging.events.BetEvent;
import com.sporty.group.jackpotservice.messaging.publisher.EventPublisher;
import com.sporty.group.jackpotservice.model.Bet;
import com.sporty.group.jackpotservice.repository.JackpotRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BetServiceImpl implements BetService {

    private final EventPublisher eventPublisher;
    private final JackpotRepository jackpotRepository;

    @Override
    public void publishBet(@NonNull Bet bet) {
        log.debug("Processing bet={}", bet);

        checkJackpotExistence(bet.jackpotId());

        BetEvent event = new BetEvent(bet);
        eventPublisher.publishBetSync(event);

        log.info("Bet with id={} and jackpotId={} successfully processed", bet.betId(), bet.jackpotId());
    }

    private void checkJackpotExistence(Long jackpotId) {
        jackpotRepository.findById(jackpotId)
                .orElseThrow(() -> new JackpotNotFoundException("Jackpot with id=%d wasn't found".formatted(jackpotId)));
    }
}
