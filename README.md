
# Jackpot Service

A Spring Boot microservice for handling jackpot contributions and rewards in gaming applications. The service manages jackpot pools, calculates contributions from bets, and distributes rewards based on configurable rules.

## Prerequisites

- Java 17 or higher
- Maven 3.6+ or higher
- Docker and Docker Compose (for Kafka)

---

## 1. Start Kafka (Required for bet processing)

```bash
docker-compose up -d
````

This will start:

* Kafka broker on port 9092
* Kafka topics creator (automatically creates `jackpot-bets` topic)

---

## 2. Run the Application

The application will start on [http://localhost:8080](http://localhost:8080)

---

## Endpoints

### 1. Submit a Bet

**POST** `/api/v1/bets`

Submit a bet to trigger jackpot contribution calculation and potential rewards.

```bash
curl -X POST http://localhost:8080/api/v1/bets \
  -H "Content-Type: application/json" \
  -d '{
    "betId": "bet-123",
    "userId": "user-456",
    "stakeAmount": 100.00,
    "jackpotId": "1"
  }'
```

**Request Body (BetDto):**

```json
{
  "betId": "string",
  "userId": "string",
  "stakeAmount": "decimal",
  "jackpotId": "string"
}
```

---

### 2. Get Bet Rewards

**GET** `/api/v1/bets/{betId}/rewards`

Retrieve all jackpot rewards associated with a specific bet.

```bash
curl http://localhost:8080/api/v1/bets/bet-123/rewards
```

**Response:**

```json
[
  {
    "id": 1,
    "betId": "bet-123",
    "userId": "user-456",
    "jackpotId": "1",
    "jackpotRewardAmount": 50.00,
    "createdAt": "2024-01-01T12:00:00"
  }
]
```
