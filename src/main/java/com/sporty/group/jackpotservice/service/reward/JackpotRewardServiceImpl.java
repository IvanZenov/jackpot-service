package com.sporty.group.jackpotservice.service.reward;

import com.sporty.group.jackpotservice.model.JackpotReward;
import com.sporty.group.jackpotservice.repository.JackpotRewardRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class JackpotRewardServiceImpl implements JackpotRewardService {

    private final JackpotRewardRepository jackpotRewardRepository;

    @Override
    public Set<JackpotReward> findAllByBetId(@NonNull String betId) {
        return jackpotRewardRepository.findAllByBetId(betId);
    }
}
