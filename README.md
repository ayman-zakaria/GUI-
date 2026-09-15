# GUI Test Automation — the-internet.herokuapp.com

## Overview
This project automates two UI scenarios against `https://the-internet.herokuapp.com` using
**Java, Selenium WebDriver, TestNG and Maven**, structured with the **Page Object Model (POM)**.

Scenarios covered:
1. **File Upload** — select and submit a file, then verify the upload succeeded.
2. **Dynamic Loading (Example 2)** — click Start, wait for the async element to render, and
   verify the displayed text is `Hello World!`.

## Design highlights
- **Page Object Model**: `HomePage`, `FileUploadPage`, `DynamicLoadingListPage` and
  `DynamicLoadingExamplePage` each encapsulate the locators and interactions for a single page.
- **Fluent design**: page-object methods return the next relevant page object (or `this`), so
  tests read as a chained flow, e.g.
  ```java
  homePage().goToFileUpload().selectFile(path).submit();
  ```
- **BasePage**: centralizes explicit-wait logic (`WebDriverWait`) and common element
  interactions, so no page object duplicates synchronization code, and no test uses
  `Thread.sleep()`.
- **DriverFactory**: a `ThreadLocal<WebDriver>` factory using WebDriverManager (no hard-coded
  driver binaries/paths), enabling safe parallel execution (see `testng.xml`,
  `parallel="methods"`).
- **Externalized configuration & data**: environment values (`base.url`, `browser`, timeouts)
  live in `src/main/resources/config.properties`; scenario data (expected text, file names)
  lives in `src/test/resources/testdata.properties`. Nothing environment- or data-specific is
  hard-coded inside page objects or test classes.
- **BaseTest**: owns the WebDriver lifecycle (`@BeforeMethod` / `@AfterMethod`) and
  automatically captures a screenshot to `target/screenshots` on failure.

## Project structure
```
src/main/java/com/assessment/gui/
  pages/     -> Page Object classes (BasePage + concrete pages)
  utils/     -> DriverFactory, ConfigManager, PropertiesReader
src/main/resources/config.properties
src/test/java/com/assessment/gui/
  base/      -> BaseTest (driver lifecycle)
  tests/     -> TestNG test classes
src/test/resources/
  testng.xml
  testdata.properties
  testfiles/sample-upload.png   -> sample file used by the upload test
```

## Prerequisites
- Java 17+
- Maven 3.8+
- Google Chrome installed (default browser; Firefox is also supported)
- Internet access to `the-internet.herokuapp.com`

## How to run
```bash
mvn clean test
```
This runs the suite defined in `src/test/resources/testng.xml`.

### Run a single test class
```bash
mvn test -Dtest=FileUploadTest
mvn test -Dtest=DynamicLoadingTest
```

### Switch browser or run headless
Edit `src/main/resources/config.properties`:
```properties
browser=chrome      # or firefox
headless=true       # or false
```

## Reports & artifacts
- TestNG's default HTML/XML reports are generated under `target/surefire-reports`.
- Screenshots for any failed test are saved to `target/screenshots/<testName>.png`.

## Notes
- Locators use stable attributes exposed by the-internet's markup (ids / link text) and avoid
  brittle XPath where a simpler, more resilient locator is available.
- All waits are explicit (`WebDriverWait` + `ExpectedConditions`); there is no reliance on
  fixed sleeps.
