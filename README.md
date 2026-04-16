# Enterprise E-Commerce Automation Framework

Production-like QA automation framework for UI and API testing.

## Tech Stack
- Java 17
- Selenium WebDriver
- TestNG
- RestAssured
- Maven
- Allure
- Jackson
- Faker
- WebDriverManager
- GitHub Actions

## Implemented Layers

### UI Layer
- Page Object Model
- reusable components
- login tests
- cart tests
- checkout tests
- sorting tests
- logout tests
- data-driven login tests

### API Layer
- reusable BaseApiClient
- AuthService
- UserService
- API assertions
- positive and negative tests
- response time validation
- API key support

### Framework Features
- screenshot on UI test failure
- Allure request/response attachments
- retry analyzer for flaky tests
- parallel execution via TestNG
- environment-based config
- CI pipeline via GitHub Actions

## Suites
- `testng-ui.xml`
- `testng-api.xml`
- `testng-smoke.xml`
- `testng-regression.xml`

## Run UI Tests
```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng-ui.xml -Denv=qa