# Customer Statement

This customer data which is provided by upstream systeam that needs to be validated before process.

## Installation

Please Import the project from git repository which is in Google Drive.


## Usage

Once the Import done.. Pls run the main class CustomerStatementApplication

## POM
The following changes are done in pom for purposefully.
1. Excluded the tomcat server and added undertow purposefully for the performance and memory usage.
2. Swagger-ui added for to test the endpoints.
3. jacoco Plugin added for the code coverage.

##Validation Business
The main business part of validation is done through validator framework. Each and every Json object and their properties are validated by hibernate validator framework.
@NotNull,@NotBlank..etc..
This solution followed the Custom validator pattern.(User defined annotations).
Two Custom validators added  @DuplicateFinder,@BalanceValidations.
Their Implementations are available in com.rabo.customer.validator.impl package.
Since we are validation List/Array of Json Objects,it's needs to wrapped for valdiation.
So added the separate model CustomerStatementWrapper.

##Unit Testing
Unit testing performed in model,controller and Service.
For controller mockmvc useed for unit testing.
For model validator used for unit testing.

##Integration Testing
For Integration testing Used TestRestTemplate to check the Integration.

##CodeCoverage
The Code coverage is above 90% since the main method not requires code coverage and it's bad practice too.


