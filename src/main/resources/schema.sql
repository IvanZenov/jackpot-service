CREATE TABLE contribution_configs
(
    id          BIGINT PRIMARY KEY,
    type        VARCHAR(50),
    config_json CLOB
);

CREATE TABLE reward_configs
(
    id          BIGINT PRIMARY KEY,
    type        VARCHAR(50),
    config_json CLOB
);


CREATE TABLE jackpots
(
    id                     VARCHAR(50) PRIMARY KEY,
    current_pool           DECIMAL(19, 2) NOT NULL,
    initial_pool           DECIMAL(19, 2) NOT NULL,
    contribution_config_id BIGINT,
    reward_config_id       BIGINT,
    version                BIGINT,
    CONSTRAINT fk_jackpot_contribution_config FOREIGN KEY (contribution_config_id) REFERENCES contribution_configs (id),
    CONSTRAINT fk_jackpot_reward_config FOREIGN KEY (reward_config_id) REFERENCES reward_configs (id)
);

CREATE TABLE jackpot_contributions
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    bet_id                 VARCHAR(50),
    user_id                VARCHAR(50),
    jackpot_id             VARCHAR(50),
    stake_amount           DECIMAL(19, 2),
    contribution_amount    DECIMAL(19, 2),
    current_jackpot_amount DECIMAL(19, 2),
    created_at             TIMESTAMP,
    CONSTRAINT fk_contribution_jackpot FOREIGN KEY (jackpot_id) REFERENCES jackpots (id)
);

CREATE TABLE jackpot_rewards
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    bet_id                VARCHAR(50),
    user_id               VARCHAR(50),
    jackpot_id            VARCHAR(50),
    jackpot_reward_amount DECIMAL(19, 2),
    created_at            TIMESTAMP,
    CONSTRAINT fk_reward_jackpot FOREIGN KEY (jackpot_id) REFERENCES jackpots (id)
);