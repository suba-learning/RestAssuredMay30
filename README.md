```markdown name=README.md
# RestAssuredMay30

A Java project for API testing and automation using RestAssured.

## Overview

This repository contains a Maven-based Java project designed for testing RESTful APIs using [RestAssured](https://rest-assured.io/). It leverages JUnit for test execution and includes dependencies for JSON parsing and schema validation.

## Features

- Write and execute automated API tests
- Validate JSON responses and schemas
- Integrate with JUnit for reporting
- Easily parse and manipulate JSON payloads

## Technologies Used

- Java
- Maven
- RestAssured (`io.rest-assured:rest-assured`)
- JUnit Jupiter (`org.junit.jupiter:junit-jupiter-api`)
- org.json for JSON handling
- Jackson Databind
- RestAssured JSON Schema Validator
- Apache Commons IO

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/suba-learning/RestAssuredMay30.git
   cd RestAssuredMay30
   ```

2. **Build the project with Maven:**
   ```bash
   mvn clean install
   ```

   Ensure you have JDK 23 or later installed, as specified in the build configuration.

## Usage

- Place your API test classes in the project's `src/test/java` directory.
- Use RestAssured static methods to build and execute HTTP requests.
- Run tests using Maven:
  ```bash
  mvn test
  ```

## Example Dependency Snippet

The project uses the following important dependencies (see `pom.xml`):

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.5.5</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.13.0</version>
    <scope>test</scope>
</dependency>
<!-- Additional dependencies for JSON handling, schema validation, and IO -->
```

## Contributing

Feel free to fork this repository and submit pull requests for improvements or additional test cases.

## License

This project currently does not have a license specified.
```
