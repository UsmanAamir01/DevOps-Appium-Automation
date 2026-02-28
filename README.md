# DevOps-Appium-Automation

A Maven-based Java test framework for Android automation using **Appium** and **TestNG**, following the **Page Object Model (POM)** design pattern.

## Project Structure

```
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
│   │   └── CheckoutTest.java         # Tests for checkout page
│   └── resources/
│       ├── apps/MyDemoApp.apk        # Demo application under test
│       └── testng.xml                # TestNG suite configuration
```

## Prerequisites

| Tool             | Version  |
|------------------|----------|
| Java JDK         | 11+      |
| Maven            | 3.8+     |
| Node.js          | 18+      |
| Appium Server    | 2.x / 3.x |
| Android Studio   | Latest   |
| Android Emulator | API 30+  |

## Setup & Run

1. **Start the Android emulator** (device name: `emulator-5554`)
2. **Start the Appium server:** `appium`
3. **Run all tests:** `mvn clean test`
4. **Run a single test class:** `mvn test -Dtest=CatalogTest`

## Test Pages Covered

| # | Page            | Test Class            | Tests |
|---|-----------------|----------------------|-------|
| 1 | Catalog         | `CatalogTest`        | 3     |
| 2 | Product Detail  | `ProductDetailTest`  | 3     |
| 3 | Cart            | `CartTest`           | 2     |
| 4 | Login           | `LoginTest`          | 2     |
| 5 | Checkout        | `CheckoutTest`       | 2     |

**Total: 12 tests across 5 pages**
