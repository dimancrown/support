# Rabo Bank Customer Statement

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.rabo.customer.CustomerStatementApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Running the application through Swagger

The application running in 8080 port and can be access through  [Swagger](http://localhost:8080/swagger-ui.html):

```shell
ulr http://localhost:8080/swagger-ui.html
```

This would contain the following controller with post method:

* Customer Statement Controller  proessCustomerReport

The request needs Holder/wrapper to validate the request.

* Sample Request for Success

```shell
{
  "customerStatement": [
    {
      "acountNumber": "NL91RABO0315273637",
      "description": "Clothes from Jan Bakker",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.6,
      "txnReference": 194261
    },
{
      "acountNumber": "NL91RABO0315273637",
      "description": "Clothes from Jan Bakker",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.6,
      "txnReference": 194262
    }
  ]
}
```
* Success Response
```shell
{
  "result": "SUCCESSFUL",
  "errorRecords": []
}
```
* DUPLICATE_REFERENCE Request
```shell
{
  "customerStatement": [
    {
      "acountNumber": "NL91RABO0315273637",
      "description": "Clothes from Jan Bakker",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.6,
      "txnReference": 194261
    },
{
      "acountNumber": "NL91RABO0315273638",
      "description": "Clothes from Jan Bakker",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.6,
      "txnReference": 194261
    }
  ]
}
```
* DUPLICATE_REFERENCE Response
```shell
{
  "result": "DUPLICATE_REFERENCE",
  "errorRecords": [
    {
      "reference": "194261",
      "accountNumber": "NL91RABO0315273637"
    },
    {
      "reference": "194261",
      "accountNumber": "NL91RABO0315273638"
    }
  ]
}
```
* INCORRECT_END_BALANCE Request
```shell
{
  "customerStatement": [
    {
      "acountNumber": "NL91RABO0315273637",
      "description": "Clothes from Jan Bakker",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.6,
      "txnReference": 194261
    },
{
      "acountNumber": "NL91RABO0315273638",
      "description": "Clothes from Jan Bakker",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.59,
      "txnReference": 194262
    }
  ]
}
```
* INCORRECT_END_BALANCE Respose
```shell
{
  "result": "INCORRECT_END_BALANCE",
  "errorRecords": [
    {
      "reference": "194262",
      "accountNumber": "NL91RABO0315273638"
    }
  ]
}
```
* DUPLICATE_REFERENCE_INCORRECT_END_BALANCE Request
```shell
{
  "customerStatement": [
    {
      "acountNumber": "NL91RABO0315273637",
      "description": "Clothes from Jan Bakker",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.6,
      "txnReference": 194261
    },
{
      "acountNumber": "NL91RABO0315273638",
      "description": "Tickets from Richard Bakker",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.59,
      "txnReference": 194262
    },
   {
      "acountNumber": "NL91RABO0315273639",
      "description": "Subscription for Peter de Vries",
      "endBalance": -20.23,
      "mutation": -41.83,
      "startBalance": 21.6,
      "txnReference": 194261
    }
  ]
}
```
* DUPLICATE_REFERENCE_INCORRECT_END_BALANCE Response
```shell
{
  "result": "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE",
  "errorRecords": [
    {
      "reference": "194261",
      "accountNumber": "NL91RABO0315273637"
    },
    {
      "reference": "194261",
      "accountNumber": "NL91RABO0315273639"
    },
    {
      "reference": "194262",
      "accountNumber": "NL91RABO0315273638"
    }
  ]
}
```

## Code coverage
Code coverage done by jacoco  maven plugin
![customer-statement-code-coverage](/code-coverage.jpg)