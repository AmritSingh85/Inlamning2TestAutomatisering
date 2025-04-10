// File: README.md

# Basketball England User Registration Tests

This project contains automated tests for the user registration functionality of the Basketball England website using Cucumber and Selenium WebDriver.

## Project Structure

- `src/test/resources/features`: Contains the Cucumber feature files
- `src/test/java/stepdefs`: Contains the step definitions
- `src/test/java/runners`: Contains the test runner

## Requirements

- Java 11 or higher
- Maven
- Chrome, Firefox, or Edge browser

## Running the Tests

To run the tests with the default browser (Chrome):

```
mvn test
```

To specify a different browser:

```
mvn test -Dbrowser=firefox
```

or

```
mvn test -Dbrowser=edge
```

## Implementation Details

### Features Implemented

1. Basic user registration scenarios:
    - Successful registration
    - Registration with missing lastname
    - Registration with mismatched passwords
    - Registration without accepting terms and conditions

2. Advanced features (for VG grade):
    - Scenario Outline with multiple test cases
    - Multi-browser testing (Chrome, Firefox, Edge)
    - Explicit waits for better synchronization