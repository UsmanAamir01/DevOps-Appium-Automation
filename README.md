# DevOps-Appium-Automation

[![Appium Android CI](https://github.com/UsmanAamir01/DevOps-Appium-Automation/actions/workflows/ci.yml/badge.svg)](https://github.com/UsmanAamir01/DevOps-Appium-Automation/actions/workflows/ci.yml)

A Maven-based Java test framework for Android automation using **Appium** and **TestNG**, following the **Page Object Model (POM)** design pattern.

## Project Structure

```
.github/
└── workflows/
    └── ci.yml                        # GitHub Actions CI pipeline
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
│   │   ├── BaseTest.java             # Setup / teardown for every test
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
│       └── testng.xml                # TestNG suite configuration
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

## Setup & Run Locally

1. **Start the Android emulator** (device name: `emulator-5554`)
2. **Start the Appium server:** `appium`
3. **Run all tests:** `mvn clean test`
4. **Run a single test class:** `mvn test -Dtest=CatalogTest`

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
| Run tests with Emulator | Boots API 34 emulator on `ubuntu-22.04`, starts Appium, runs `mvn test` |
| Upload reports | Saves Surefire XML/HTML reports as build artifacts |
| Upload Appium log | Saves `appium.log` (always) |
| Upload screenshots | Saves failure screenshots on failure |

## Test Cases Covered

| # | Page            | Test Class                    | Tests |
|---|-----------------|-------------------------------|-------|
| 1 | Catalog         | `CatalogTest`                 | 3     |
| 2 | Product Detail  | `ProductDetailTest`           | 3     |
| 3 | Cart            | `CartTest`                    | 3     |
| 4 | Login           | `LoginTest`                   | 2     |
| 5 | Checkout        | `CheckoutTest`                | 2     |
| 6 | End-to-End      | `EndToEndTest`                | 1     |
| 7 | Functional 1–5  | `FunctionalTestCases1to5`     | 5     |
| 8 | Functional 6–10 | `FunctionalTestCases6to10`    | 5     |

**Total: 24 tests across 8 test classes**
