# DevOps-Appium-Automation

[![Appium Android CI](https://github.com/UsmanAamir01/DevOps-Appium-Automation/actions/workflows/ci.yml/badge.svg)](https://github.com/UsmanAamir01/DevOps-Appium-Automation/actions/workflows/ci.yml)

A Maven-based Java test framework for Android automation using **Appium** and **TestNG**, following the **Page Object Model (POM)** design pattern.

## Project Structure

```
.github/
└── workflows/
    ├── ci.yml                        # GitHub Actions CI pipeline
    └── docker-ci.yml                 # Docker-based CI pipeline
src/
├── main/java/com/automation/
│   ├── base/
│   │   └── AppDriver.java            # Centralised AndroidDriver factory
│   ├── pages/
│   │   ├── BasePage.java             # Abstract base for all Page Objects
│   │   ├── CatalogPage.java          # Products listing screen
│   │   ├── ProductDetailPage.java    # Product detail screen
│   │   ├── CartPage.java             # Shopping cart screen
│   │   ├── LoginPage.java            # Login screen
│   │   └── CheckoutPage.java         # Checkout / shipping address screen
│   └── utils/
│       └── TestUtils.java            # Screenshot capture & utilities
├── test/
│   ├── java/com/automation/tests/
│   │   ├── BaseTest.java             # Setup / teardown (ThreadLocal driver)
│   │   ├── CatalogTest.java          # Tests for catalog page
│   │   ├── ProductDetailTest.java    # Tests for product detail page
│   │   ├── CartTest.java             # Tests for cart page
│   │   ├── LoginTest.java            # Tests for login page
│   │   ├── CheckoutTest.java         # Tests for checkout page
│   │   ├── EndToEndTest.java         # Full E2E navigation test
│   │   ├── FunctionalTestCases1to5.java  # TC-01 to TC-05
│   │   └── FunctionalTestCases6to10.java # TC-06 to TC-10
│   └── resources/
│       ├── apps/MyDemoApp.apk        # Demo application under test
│       ├── allure.properties         # Allure report configuration
│       └── testng.xml                # TestNG suite (parallel enabled)
Dockerfile                            # Test runner Docker image
docker-compose.yml                    # Appium + test runner orchestration
```

## Prerequisites

| Tool             | Version   |
|------------------|-----------|
| Java JDK         | 17+       |
| Maven            | 3.8+      |
| Node.js          | 20+       |
| Appium Server    | 3.x       |
| Android Studio   | Latest    |
| Android Emulator | API 30+   |
| Docker (optional)| 24+       |

## Setup & Run Locally

1. **Start the Android emulator** (device name: `emulator-5554`)
2. **Start the Appium server:** `appium`
3. **Run all tests:** `mvn clean test`
4. **Run a single test class:** `mvn test -Dtest=CatalogTest`
5. **Generate Allure report:** `mvn allure:report` (view at `target/site/allure-maven-plugin/index.html`)

## Parallel Test Execution

Tests are configured for parallel execution via TestNG:

- **Mode:** `parallel="tests"` — each `<test>` block runs in its own thread
- **Thread count:** 3 concurrent threads
- **Thread safety:** `BaseTest` uses `ThreadLocal<AndroidDriver>` for driver isolation

> **Note:** With a single emulator, Appium serializes sessions. True parallelism requires multiple emulators/devices.

## Test Reports (Allure)

The framework uses [Allure](https://docs.qameta.io/allure/) for interactive, rich test reports:

```bash
# Run tests and generate report
mvn clean test
mvn allure:report

# Open report in browser
# Report is at: target/site/allure-maven-plugin/index.html
```

In CI, Allure reports are uploaded as build artifacts and can be downloaded from the Actions run summary.

## Docker Setup

Run the complete test environment using Docker:

```bash
# Build and run tests
docker-compose up --build --abort-on-container-exit

# View reports (after tests complete)
# Reports are at: ./target/site/allure-maven-plugin/index.html

# Cleanup
docker-compose down --volumes --remove-orphans
```

A separate Docker CI workflow is available via **Actions → Docker Appium CI → Run workflow**.

## CI Pipeline (GitHub Actions)

The pipeline is defined in `.github/workflows/ci.yml` and triggers automatically on:
- Every **push to `main`**
- Every **Pull Request targeting `main`**

### Pipeline Steps

| Step | Description |
|------|-------------|
| Checkout | Clones the repository |
| Set up JDK 17 | Installs Temurin JDK 17 with Maven cache |
| Pin JAVA_HOME | Ensures JDK 17 is used by all steps including emulator setup |
| Set up Node.js 20 | Required for Appium 3.x |
| Install Appium | Installs Appium globally + `uiautomator2` driver |
| Enable KVM | Hardware acceleration for Android emulator on Linux |
| Build Maven project | Runs `mvn clean compile` |
| Run tests with Emulator | Boots API 34 emulator on `ubuntu-latest`, starts Appium, runs `mvn test` |
| Generate Allure report | Produces interactive HTML test report |
| Upload reports | Saves Surefire XML/HTML + Allure reports as build artifacts |
| Upload Appium log | Saves `appium.log` (always) |
| Upload screenshots | Saves failure screenshots on failure |

## Test Cases Covered

| # | Page            | Test Class                    | Tests |
|---|-----------------|-------------------------------|-------|
| 1 | Catalog         | `CatalogTest`                 | 3     |
| 2 | Product Detail  | `ProductDetailTest`           | 3     |
| 3 | Cart            | `CartTest`                    | 3     |
| 4 | Login           | `LoginTest`                   | 3     |
| 5 | Checkout        | `CheckoutTest`                | 2     |
| 6 | End-to-End      | `EndToEndTest`                | 1     |
| 7 | Functional 1–5  | `FunctionalTestCases1to5`     | 5     |
| 8 | Functional 6–10 | `FunctionalTestCases6to10`    | 5     |

**Total: 25 tests across 8 test classes**
