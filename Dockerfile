# ──────────────────────────────────────────────────────────────────────
# Test Runner Image — Maven + JDK 17
# Runs the Appium test suite against a remote Appium server.
# ──────────────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Cache Maven dependencies first
COPY pom.xml .
RUN mvn dependency:resolve -B -q

# Copy project source
COPY src/ src/

# Compile (tests are compiled at test time)
RUN mvn compile -B -q

# ──────────────────────────────────────────────────────────────────────
# Runtime: run tests against an external Appium server
# ──────────────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine

WORKDIR /app

# Copy the entire project (with cached .m2)
COPY --from=builder /root/.m2 /root/.m2
COPY --from=builder /app /app

# Default environment variables (override via docker-compose or CLI)
ENV APPIUM_URL=http://appium:4723
ENV DEVICE_NAME=emulator-5554
ENV APP_PATH=/app/src/test/resources/apps/MyDemoApp.apk

# Run tests and generate Allure report
CMD ["sh", "-c", "mvn test -B && mvn allure:report -B || true"]
