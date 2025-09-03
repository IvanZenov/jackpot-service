package com.sporty.group.jackpotservice.messaging.events;

import com.sporty.group.jackpotservice.model.Bet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetEvent {
    private Bet bet;
}
