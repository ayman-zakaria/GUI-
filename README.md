# GUI Test Automation — the-internet.herokuapp.com

## Overview
This project automates two UI scenarios against `https://the-internet.herokuapp.com` using
**Java, Selenium WebDriver, TestNG and Maven**, structured with the **Page Object Model (POM)**.

Scenarios covered:
1. **File Upload** — select and submit a file, then verify the upload succeeded.
2. **Dynamic Loading (Example 2)** — click Start, wait for the async element to render, and
   verify the displayed text is `Hello World!`.

## Design highlights
- **Page Object Model** (`Pages` package): `HomePage`, `FileUploadPage`,
  `DynamicLoadingListPage` and `DynamicLoadingExamplePage` each hold their own locators
  (grouped under a `/* locators */` comment block) and expose only page-specific behaviour
  (`/* methods */`). Each page holds its own `WebDriver` instance field (not static), so
  pages are safe to use across parallel threads.
- **Fluent design**: page methods return the next relevant page object (or `this`), so tests
  read as a single chained flow:
  ```java
  homePage().goToFileUpload().selectFile(path).submit();
  ```
- **`BasesAndConfig` package** — shared, reusable building blocks so no logic is duplicated
  across page objects:
  - `Waits` — static explicit-wait helpers (`visible`, `clickable`, `invisible`); the timeout
    itself comes from `config.properties`, not a hard-coded literal.
  - `ScrollUtils` — scrolls an element into view before it's interacted with.
  - `ElementActions` — the single entry point pages use for `sendData` / `clickElement` /
    `getText`; each call waits, scrolls, then acts, so this sequence is never repeated.
  - `Screenshot` — captures a screenshot, attaches it to the Allure report, and saves it to
    `target/screenshots`.
  - `LogUtil` — a small static wrapper over log4j2.
  - `ConfigReader` / `ConfigManager` — generic classpath properties loader + typed accessors
    for environment values (base URL, browser, timeouts).
- **`Drivers` package** — separates *how* a driver is built from *how* it's managed:
  - `BrowserFactory` builds a configured Chrome/Firefox instance via WebDriverManager (no
    fixed local path to a driver binary) using config-driven headless/timeout settings.
  - `DriverManager` owns a `ThreadLocal<WebDriver>` so each test thread gets its own driver,
    enabling safe parallel execution (see `testng.xml`, `parallel="methods"`).
- **`Listeners` package** — `TestListener` implements TestNG's `IExecutionListener` /
  `ITestListener` to: clear stale Allure results before a run starts, log every test's
  outcome, and automatically capture a screenshot the moment a test fails. It's wired once
  via `@Listeners(TestListener.class)` on `tests.BaseTest`, so every test class inherits it
  without repeating the annotation.
- **Externalized configuration & data**: environment values live in
  `src/main/resources/config.properties`; scenario data (expected text, file name) lives in
  `src/test/resources/testdata.properties`. Nothing environment- or data-specific is
  hard-coded inside page objects or test classes.
- **Allure reporting**: `@Step` annotations on every page-object action produce a readable
  step-by-step report per test, with failure screenshots attached automatically.

## Project structure
```
src/main/java/
  BasesAndConfig/  -> ConfigReader, ConfigManager, Waits, ScrollUtils, ElementActions,
                      Screenshot, LogUtil
  Drivers/         -> BrowserFactory, DriverManager
  Listeners/       -> TestListener
  Pages/           -> HomePage, FileUploadPage, DynamicLoadingListPage, DynamicLoadingExamplePage
src/main/resources/
  config.properties
  log4j2.properties
  allure.properties
src/test/java/tests/
  BaseTest.java    -> driver lifecycle (@BeforeMethod/@AfterMethod), @Listeners wiring
  FileUploadTest.java
  DynamicLoadingTest.java
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
- Log files are written to `target/logs` (see `log4j2.properties`); console output is
  colorized by level.
- Screenshots for any failed test are saved to `target/screenshots/<testName>.png` and
  attached to the Allure report automatically via `TestListener` + `Screenshot`.
- **Allure report**: results are written to `target/allure-results` on every `mvn test` run.
  To view the report:
  ```bash
  mvn allure:serve
  ```
  This downloads the Allure commandline automatically (no separate install needed), builds
  the report from `target/allure-results`, and opens it in your browser. Use `mvn allure:report`
  instead if you just want the static HTML written to `target/site/allure-maven-plugin`
  without opening a browser.

## Notes
- Locators use stable attributes exposed by the-internet's markup (ids / link text) and avoid
  brittle XPath where a simpler, more resilient locator is available.
- All waits are explicit (`WebDriverWait` via `BasesAndConfig.Waits`); there is no reliance on
  fixed sleeps.
