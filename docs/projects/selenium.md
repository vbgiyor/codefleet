# Selenium Project

## Overview
The `selenium` project is a Java-based UI testing framework using Selenium WebDriver and TestNG. It includes two independent projects: `PageInspector` (tests Herokuapp website) and `CFInspector` (tests Codefleet website).

## Project Configuration

### PageInspector
- **Location**: `automation/ui/selenium/java/PageInspector/HerokuApp`
- **Main Package**: `modules`
- **Dependencies**: Defined in `pom.xml`
- **Tests**: Located in `src/test/java/modules/tests`
- **CI**: Configured in `.github/workflows/selenium_ci.yml`
- **Browser**: Chrome (headless mode supported)

### CFInspector
- **Location**: `automation/ui/selenium/java/CFInspector`
- **Main Package**: `com.codefleet.cfinspector.modules`
- **Dependencies**: Defined in `pom.xml`
- **Tests**: Located in `src/test/java/com/codefleet/cfinspector/modules/tests`
- **CI**: Configured in `.github/workflows/website_and_cfinspector_ci.yml`
- **Browser**: Chrome (headless mode supported)

## Setup Instructions

### Prerequisites
- JDK 17 and Maven
- Chrome browser
- Docker (optional, for containerized runs)
- Codefleet website running locally for CFInspector (see `website.md`)

### PageInspector Setup
1. Ensure JDK 17 and Maven are installed:
   ```bash
   java -version
   mvn -version
   ```
2. Install Chrome browser:
   ```bash
   google-chrome --version
   ```
3. Navigate to the project directory:
   ```bash
   cd automation/ui/selenium/java/PageInspector/HerokuApp
   ```

### CFInspector Setup
1. Start the Codefleet website:
   ```bash
   cd website/backend
   mkdir -p logs
   docker-compose up -d
   python manage.py runserver
   ```
2. Ensure JDK 17 and Maven are installed (as above).
3. Install Chrome browser (as above).
4. Navigate to the project directory:
   ```bash
   cd automation/ui/selenium/java/CFInspector
   ```

## How to Run

### PageInspector (Using Docker)
1. Build Docker image:
   ```bash
   cd automation/ui/selenium/java/PageInspector/HerokuApp
   docker build -t selenium-herokuapp .
   ```
2. Run tests:
   ```bash
   docker run --rm -e BROWSER=chrome -e HEADLESS=true selenium-herokuapp mvn clean test -Dtestng.xml=testng.xml
   ```
3. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

### PageInspector (Without Docker)
1. Install dependencies and run tests:
   ```bash
   cd automation/ui/selenium/java/PageInspector/HerokuApp
   mvn clean test -Dtestng.xml=testng.xml -DBROWSER=chrome -DHEADLESS=true
   ```
2. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

### CFInspector (Using Docker)
1. Ensure Codefleet website is running (see Setup).
2. Build Docker image:
   ```bash
   cd automation/ui/selenium/java/CFInspector
   docker build -t selenium-cfinspector .
   ```
3. Run tests:
   ```bash
   docker run --rm -e BROWSER=chrome -e HEADLESS=true -e BASE_URL=http://host.docker.internal:8000 selenium-cfinspector mvn clean test -Dtestng.xml=testng.xml
   ```
4. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

### CFInspector (Without Docker)
1. Ensure Codefleet website is running (see Setup).
2. Move to project directory:
   ```bash
   cd automation/ui/selenium/java/CFInspector
   ```
   To run TestNG tests with Maven using different XML configurations and options, you would typically use the following Maven command structure. Below is a list of valid commands with various options you might use for executing your test suites in Codefleet.

   ### 1. **Basic TestNG Suite Run**

   To run a specific TestNG suite (`testng.xml`) using Maven:

   ```bash
   mvn test -DsuiteXmlFile=testng.xml
   ```

   Where `testng.xml` can be any of your XML configurations like `Firefox.xml`, `parallel.xml`, or `default.xml`.

   ### 2. **With Browser Parameter (e.g., Firefox, Chrome)**

   To specify the browser to use during test execution (this is passed as a parameter to the tests):

   ```bash
   mvn test -DsuiteXmlFile=Firefox.xml -Dbrowser=firefox
   ```

   Or for Chrome:

   ```bash
   mvn test -DsuiteXmlFile=parallel.xml -Dbrowser=chrome
   ```

   ### 3. **With Headless Mode**

   To run the tests in headless mode (no UI), you can add an environment variable or a system property to trigger headless execution. For example, for Firefox:

   ```bash
   mvn test -DsuiteXmlFile=Firefox.xml -Dbrowser=firefox -Dheadless=true
   ```

   For Chrome:

   ```bash
   mvn test -DsuiteXmlFile=parallel.xml -Dbrowser=chrome -Dheadless=true
   ```

   ### 4. **Parallel Execution**

   You can specify parallel execution in your `parallel.xml` configuration by setting the `parallel` attribute in the XML itself. If you want to manually control thread count, you can pass it as a system property:

   ```bash
   mvn test -DsuiteXmlFile=parallel.xml -DthreadCount=2
   ```

   ### 5. **Generate a Report**

   To generate an HTML or TestNG report:

   ```bash
   mvn test -DsuiteXmlFile=Firefox.xml -Dreport=true
   ```

   You would need to have a reporting plugin configured in your `pom.xml` (e.g., TestNG Report Plugin or Allure).

   ### 6. **Skip Certain Tests or Groups**

   To skip certain groups or tests, you can use the `-Dgroups` or `-DexcludedGroups` parameter.

   To run only tests from a specific group:

   ```bash
   mvn test -DsuiteXmlFile=default.xml -Dgroups="smoke"
   ```

   To exclude tests from a specific group:

   ```bash
   mvn test -DsuiteXmlFile=default.xml -DexcludedGroups="slow"
   ```

   ### 7. **TestNG Parameters**

   To pass custom parameters to the tests, you can use `-DparameterName=value`. For example, if you want to pass a custom `username` or `password` to the tests:

   ```bash
   mvn test -DsuiteXmlFile=Firefox.xml -Dusername=testuser -Dpassword=testpassword
   ```

   ### 8. **Running Specific Tests or Classes**

   If you only want to run specific classes or methods from your test suite, you can specify them in the command.

   To run a specific class:

   ```bash
   mvn test -DsuiteXmlFile=default.xml -Dtest=com.codefleet.cfinspector.modules.tests.HomePageTest
   ```

   Or to run a specific test method:

   ```bash
   mvn test -DsuiteXmlFile=default.xml -Dtest=com.codefleet.cfinspector.modules.tests.HomePageTest#testHomePage
   ```

   ### 9. **Skip Tests**

   If you want to skip tests during a build (e.g., skipping unit tests but still running integration tests):

   ```bash
   mvn install -DskipTests
   ```

   Or skip tests but still run the test phase:

   ```bash
   mvn test -DskipTests
   ```

   ### 10. **Clear Previous Reports**

   To clear old reports before running the tests, you can delete the `target` folder before the execution:

   ```bash
   mvn clean test -DsuiteXmlFile=default.xml
   ```

   This command will clean the previous build results and generate fresh ones.

   ### 11. **Other Useful Options**

   * **Skip the Maven Surefire Plugin**: If you want to skip the Surefire plugin (used to run tests):

   ```bash
   mvn clean install -Dmaven.test.skip=true
   ```

   * **Run Tests Only in a Specific Package**: You can specify tests to run in a package:

   ```bash
   mvn test -DsuiteXmlFile=default.xml -Dtest=com.codefleet.cfinspector.modules.tests.*
   ```

   ---

   ### Summary of Common Options:

   * `-DsuiteXmlFile=path/to/testng.xml` — Path to the XML suite file.
   * `-Dbrowser=firefox` or `-Dbrowser=chrome` — Set the browser.
   * `-Dheadless=true` — Run tests in headless mode.
   * `-DthreadCount=n` — Number of threads for parallel execution.
   * `-Dreport=true` — Enable report generation.
   * `-Dgroups="group_name"` — Run specific groups of tests.
   * `-DexcludedGroups="group_name"` — Exclude specific groups from execution.
   * `-DparameterName=value` — Pass parameters to tests.
   * `-DskipTests=true` — Skip running tests but still build the project.

3. View reports:
   - Reports: `target/surefire-reports/`
   - Screenshots (if failed): `target/screenshots/`

## Project Details

### PageInspector
- **Framework**: Selenium WebDriver, TestNG
- **Key Classes**:
  - `HerokuappHomePage`: Page object for Herokuapp.
  - `BasePageTest`: Base test class.
  - `CoreActions`: Core Selenium actions.
  - `WebDriverFactory`: Manages WebDriver instances.
  - `LoggerUtil`, `ScreenshotUtil`, `WaitUtil`: Utility classes.
- **Tests**: `HerokuappHomePageTest` (in `src/test/java/modules/tests`).
- **Configuration**:
  - `data.properties`: Test data.
  - `locators.properties`: Element locators.
  - `webapp.properties`: App-specific settings.
- **Linting**: Checkstyle with `checkstyle.xml`.

### CFInspector
- **Framework**: Selenium WebDriver, TestNG
- **Key Classes**:
  - `BasicAuthPage`, `HomePage`, `AutomationPage`, `ABTestPage`, etc.: Page objects for Codefleet website.
  - `BasePageTest`: Base test class.
  - `CoreActions`, `WebDriverFactory`, `LocatorParser`: Core functionality.
  - `LoggerUtil`, `ScreenShotUtil`, `WaitForElementsUtil`: Utility classes.
- **Tests**:
  - `HomePageTest` (in `src/test/java/com/codefleet/cfinspector/modules/tests/homepage`).
  - `ABTestPageTest` (in `src/test/java/com/codefleet/cfinspector/modules/tests/selenium/page/abtestpage`).
- **Configuration**:
  - `data.properties`: Test data.
  - `locators.properties`: Element locators.
- **Linting**: Checkstyle with `checkstyle.xml`.

## Testing Guidelines
- **PageInspector**: Tests Herokuapp homepage and features.
- **CFInspector**: Tests Codefleet website features (e.g., Basic Auth, navigation).
- Use headless mode for CI (`HEADLESS=true`).
- Check `target/surefire-reports/` for results and `target/screenshots/` for failures.
- For CFInspector, ensure `http://localhost:8000` is accessible.

## Recent Changes
- Added `CFInspector` to test Codefleet website, including Basic Auth (`BasicAuthPage`).
- Updated CI pipeline (`website_and_cfinspector_ci.yml`) to support CFInspector and Django tests.

*Last Updated: May 15, 2025*