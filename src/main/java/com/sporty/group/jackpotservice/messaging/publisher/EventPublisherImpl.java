package com.sporty.group.jackpotservice.messaging.publisher;

import com.sporty.group.jackpotservice.messaging.events.BetEvent;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventPublisherImpl implements EventPublisher {

    private final KafkaOperations<String, BetEvent> kafkaOperations;
    private final String jackpotBetsTopic;

    public EventPublisherImpl(KafkaOperations<String, BetEvent> kafkaOperations,
                              @Value("${kafka.topics.jackpotBets}") String jackpotBetsTopic) {
        this.kafkaOperations = kafkaOperations;
        this.jackpotBetsTopic = jackpotBetsTopic;
    }

    @Override
    public void publishBetSync(@NonNull BetEvent betEvent) {
        try {
            kafkaOperations.send(jackpotBetsTopic, betEvent.getBet().betId(), betEvent).get();
        } catch (Exception e) {
            log.error("Impossible to publish betEvent={}", betEvent, e);
            throw new RuntimeException("Impossible to publish betEvent", e);
        }
    }
}
