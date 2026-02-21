# AiPIWatch

HTTP request logging and analytics REST API built with Spring Boot.

## How to run

- Build: mvn clean package
- Run: mvn spring-boot:run

The app runs on http://localhost:8081 by default.

## API

Base URL: http://localhost:8081

### Test endpoints (generate logs)
- GET http://localhost:8081/test/fast
	- Returns: "fast"
- GET http://localhost:8081/test/slow
	- Sleeps ~800ms, returns: "slow"

### Log endpoints
- GET http://localhost:8081/logs
	- Returns all request logs as JSON array.
- GET http://localhost:8081/logs/count
	- Returns total number of logs as a number.
- GET http://localhost:8081/logs/slow?threshold=500
	- Returns only logs with durationMs greater than the threshold.
- GET http://localhost:8081/logs/top-endpoints
	- Returns a JSON object of endpoint -> count, ordered by most frequent.

## How to test and what to expect
1) Call /test/fast and /test/slow a few times.
2) Open /logs to see the recorded entries.
3) Open /logs/count to see the total count increase.
4) Open /logs/slow?threshold=500 to see only the slow requests (the /test/slow ones).
5) Open /logs/top-endpoints to see the most frequently called endpoints.
