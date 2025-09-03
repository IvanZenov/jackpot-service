package com.sporty.group.jackpotservice.repository;

import com.sporty.group.jackpotservice.model.JackpotReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface JackpotRewardRepository extends JpaRepository<JackpotReward, Long> {
    Set<JackpotReward> findAllByBetId(String betId);
}
