# Selenium Project

## Overview
The `selenium` project is a Java-based UI testing framework using Selenium WebDriver and TestNG. It tests the Herokuapp website.

## Project Configuration
- **Location**: `automation/ui/selenium/java/PageInspector/HerokuApp`
- **Main Package**: `modules`
- **Dependencies**: Defined in `automation/ui/selenium/java/PageInspector/HerokuApp/pom.xml`
- **Tests**: Located in `src/test/java/modules/tests`
- **CI**: Configured in `.github/workflows/selenium_ci.yml`
- **Browser**: Chrome (headless mode supported)

## How to Run

### Using Docker
1. Build Docker image:
   ```bash
   cd automation/ui/selenium/java/PageInspector/HerokuApp
   docker build -t selenium-herokuapp .
   ```
2. Run tests in Docker:
   ```bash
   docker run --rm -e BROWSER=chrome -e HEADLESS=true selenium-herokuapp mvn clean test -Dtestng.xml=testng.xml
   ```
3. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

### Without Docker
1. Ensure JDK 17 and Maven are installed.
2. Install Chrome browser.
3. Navigate to project directory:
   ```bash
   cd automation/ui/selenium/java/PageInspector/HerokuApp
   ```
4. Install dependencies and run tests:
   ```bash
   mvn clean test -Dtestng.xml=testng.xml -DBROWSER=chrome -DHEADLESS=true
   ```
5. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

## Project Details
- **Framework**: Selenium WebDriver, TestNG
- **Key Classes**:
  - `HerokuappHomePage`: Page object for Herokuapp
  - `BasePageTest`: Base test class
- **Tests**: `HerokuappHomePageTest`
- **Linting**: Checkstyle with custom configuration