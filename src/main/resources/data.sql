-- Contribution configs
INSERT INTO contribution_configs (id, type, config_json)
VALUES (1, 'FIXED', '{"percent":5}');

INSERT INTO contribution_configs (id, type, config_json)
VALUES (2, 'VARIABLE', '{
    "percentInitial": 10,
    "percentMin": 2,
    "decreasePerStep": 1,
    "growthInterval": 1000
}');


-- Rewards config
INSERT INTO reward_configs (id, type, config_json)
VALUES (1, 'FIXED', '{"chancePercent":10}');

-- Variable reward example
INSERT INTO reward_configs (id, type, config_json)
VALUES (2, 'VARIABLE', '{
    "baseChancePercent": 1,
    "maxChancePercent": 100,
    "growthThreshold": 5000
}');


-- Jackpots
INSERT INTO jackpots (id, current_pool, initial_pool, contribution_config_id, reward_config_id, version)
VALUES (1, 1000, 1000, 2, 2, 0);

-- Jackpot using variable contribution + reward
INSERT INTO jackpots (id, current_pool, initial_pool, contribution_config_id, reward_config_id, version)
VALUES (2, 10000, 10000, 1, 1, 0);
