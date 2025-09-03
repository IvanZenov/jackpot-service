package com.sporty.group.jackpotservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jackpot_contributions")
public class JackpotContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String betId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long jackpotId;

    @Column(nullable = false)
    private BigDecimal stakeAmount;

    @Column(nullable = false)
    private BigDecimal contributionAmount;

    @Column(nullable = false)
    private BigDecimal currentJackpotAmount;

    @Column(nullable = false)
    private Instant createdAt;
}
