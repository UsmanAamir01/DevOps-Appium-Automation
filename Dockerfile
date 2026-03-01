# ──────────────────────────────────────────────────────────────────────
# Test Runner Image — Maven + JDK 17
# ──────────────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Cache Maven dependencies first
COPY pom.xml .
RUN mvn dependency:resolve -B -q

# Copy project source
COPY src/ src/

# Compile
RUN mvn compile -B -q

# ──────────────────────────────────────────────────────────────────────
# Runtime: run tests against the Docker Android emulator
# ──────────────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine

RUN apk add --no-cache curl

WORKDIR /app

COPY --from=builder /root/.m2 /root/.m2
COPY --from=builder /app /app

# Default env vars (overridden by docker-compose)
ENV APPIUM_URL=http://android-emulator:4723
ENV DEVICE_NAME=emulator-5554
ENV APP_PATH=/root/tmp/MyDemoApp.apk
ENV CI=true
ENV DOCKER_MODE=true

# Run tests then generate Allure report
CMD ["sh", "-c", "mvn test -B; TEST_EXIT=$?; mvn allure:report -B || true; exit $TEST_EXIT"]
