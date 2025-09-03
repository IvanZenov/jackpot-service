package com.sporty.group.jackpotservice.messaging.publisher;

import com.sporty.group.jackpotservice.messaging.events.BetEvent;

public interface EventPublisher {
    void publishBetSync(BetEvent betEvent);
}
