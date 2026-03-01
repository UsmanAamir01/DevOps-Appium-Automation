# DevOps Appium Automation Engine 🚀📱

A modern, robust, and highly scalable **Appium Mobile Automation** framework designed from the ground up for **DevOps CI/CD integration**, **Parallel Execution**, and **Dockerized environments**.

## 🔥 Key Features

- **Parallel Test Execution:** Built with `ThreadLocal` WebDrivers for thread-safe, concurrent test execution across multiple devices using TestNG.
- **Hybrid Docker Architecture:** Runs tests and Appium fully containerized via `docker-compose`, securely proxying ADB to the host machine's emulator for perfect local/CI parity without nested KVM limits.
- **Rich Allure Reporting:** Beautiful, interactive HTML test reports with screenshots attached automatically on test failures.
- **Flaky Test Resilience:** Configured with `surefire` automatic retries and generous element polling for stable CI runs.
- **Modular Object-Oriented Design:** Uses the Page Object Model (POM) and robust Wait utilities.

---

## 🏗️ Project Structure

```text
├── .github/workflows/
│   └── ci.yml                # Main CI Pipeline (Build, Docker, Test, Report)
├── src/test/java/
│   ├── com.automation.pages/ # Page Object Model classes
│   ├── com.automation.tests/ # Test classes & ThreadLocal BaseTest
│   └── com.automation.utils/ # Waiters and Screenshot utilities
├── src/test/resources/
│   ├── apps/                 # APK files (e.g., MyDemoApp.apk)
│   ├── allure.properties     # Allure report configuration
│   └── testng.xml            # TestNG suite config (handles parallel execution)
├── docker-compose.yml        # Hybrid Docker stack (Appium + Test Runner containers)
├── Dockerfile                # Multi-stage Maven build for Test Runner container
└── pom.xml                   # Maven dependencies and Surefire/Allure plugins
```

---

## 🏃‍♂️ Running Tests Locally

### Pre-requisites
- **Java JDK 17**
- **Maven 3.9+**
- **Android Studio** (for Emulator/SDK)
- **Appium 2.x** (if running natively without Docker)

### Option 1: Standard Maven Execution (Native)
Start your Appium server and Android Emulator locally on port `4723` and device `emulator-5554`, then run:
```bash
mvn clean test
```

### Option 2: Hybrid Docker Execution (Recommended) 🐳
The most reliable way to run tests mimicking the CI environment. This spins up Appium and Maven **inside Docker**, connecting to your host's Android Emulator.

1. **Start your Android Emulator** via Android Studio. Wait until you see the Home Screen.
2. Run the Docker stack:
```bash
docker compose up --build --abort-on-container-exit --exit-code-from tests
```
*Note: Make sure port `4723` is not already in use by a native Appium server.*

---

## 📊 Generating Allure Reports

After running the tests (via Native or Docker), the raw data is saved to `target/allure-results`. To generate and view the beautiful HTML report:

```bash
mvn allure:report
```
The interactive HTML report will be generated at:
`target/site/allure-maven-plugin/index.html`

*(You can simply open this file in your browser!)*

---

## 🌩️ CI/CD Pipeline (GitHub Actions)

This repository features an advanced Google GitHub Actions pipeline (`ci.yml`) that executes on every `push` and `pull_request` to the `main` branch.

**The Pipeline Flow:**
1. Validates the Maven build.
2. Boots an **Android Emulator natively** via the `reactivecircus` action (bypassing macOS/Linux virtualization limits).
3. Exposes the host's ADB daemon.
4. Spins up the **Appium Server & Maven Tests in Docker**, proxying the connection to the host emulator.
5. Generates the **Allure Test Report**.
6. Archives screenshots, test reports, and Docker logs as downloadable artifacts.

---

## ⚡ Parallel Execution Setup

The framework is architecturally ready for **true parallel cross-device execution**.
The `BaseTest.java` protects the `AndroidDriver` instance using `ThreadLocal<AndroidDriver>`, ensuring tests do not corrupt each other's sessions.

To scale up:
1. Attach multiple emulators or physical devices.
2. Update `testng.xml` to define multiple `<test>` blocks with different device capabilities.
3. TestNG will execute them concurrently based on the `thread-count="3"` and `parallel="tests"` attributes.

---

## 🛠️ Tech Stack
- **Language**: Java 17
- **Build Tool**: Maven
- **Testing Framework**: TestNG
- **Mobile Automation**: Appium Java Client 9.x / UiAutomator2
- **Reporting**: Allure
- **Containerization**: Docker & Docker Compose
- **CI/CD**: GitHub Actions
