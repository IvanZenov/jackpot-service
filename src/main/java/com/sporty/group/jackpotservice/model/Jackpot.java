package com.sporty.group.jackpotservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "jackpots")
public class Jackpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private BigDecimal currentPool = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal initialPool;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contribution_config_id")
    private ContributionConfig contributionConfig;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_config_id")
    private RewardConfig rewardConfig;

    @Version
    private Long version;

    public BigDecimal getGrowthSinceStart() {
        return currentPool.subtract(initialPool).max(BigDecimal.ZERO);
    }

    public void increaseCurrentPool(@NonNull BigDecimal amount) {
        this.currentPool = this.currentPool.add(amount);
    }

    public void resetCurrentPoolToInitial() {
        this.currentPool = this.initialPool;
    }
}
