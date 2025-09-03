package com.sporty.group.jackpotservice.messaging.handler;

import com.sporty.group.jackpotservice.manager.BetManager;
import com.sporty.group.jackpotservice.messaging.events.BetEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BetConsumer {
    private final BetManager betManager;

    @KafkaListener(topics = "${kafka.topics.jackpotBets}", groupId = "jackpot-service-group")
    public void handle(@NonNull BetEvent event) {
        log.debug("Processing betEvent={}", event);

        betManager.processBet(event.getBet());

        log.debug("Processed betEvent={} successfully finished", event);
    }
}
