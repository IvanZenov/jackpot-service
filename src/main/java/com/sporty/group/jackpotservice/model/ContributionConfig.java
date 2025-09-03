package com.sporty.group.jackpotservice.model;

import com.sporty.group.jackpotservice.model.config.ContributionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "contribution_configs")
public class ContributionConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContributionType type;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String configJson;
}
