# ── Test Runner: Maven + JDK 17 ───────────────────────────────────
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:resolve dependency:resolve-plugins -B -q || true

# Copy source and compile
COPY src/ src/
RUN mvn compile -B -q

# ── Runtime ───────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17

RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=builder /root/.m2 /root/.m2
COPY --from=builder /app /app

ENV CI=true

# Run tests, then always generate Allure report regardless of test outcome
CMD ["sh", "-c", "mvn test -B; TEST_EXIT=$?; mvn allure:report -B 2>/dev/null || true; exit $TEST_EXIT"]
