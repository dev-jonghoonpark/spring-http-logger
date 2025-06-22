# spring-http-logger

This library assists logging for Spring HTTP clients (RestClient, WebClient).

## Download

### Gradle

```groovy
implementation 'com.jonghoonpark:spring-http-logger:1.0.0'
```

### Maven

```xml
<dependency>
    <groupId>com.jonghoonpark</groupId>
    <artifactId>spring-http-logger</artifactId>
    <version>1.0.0</version>
</dependency>
```

## How to use

### RestClient

Use `ClientLoggerRequestInterceptor`.

```java
RestClient restClient = RestClient.builder()
    .requestInterceptor(new ClientLoggerRequestInterceptor())
    .build();
```

### WebClient (reactive)

Use `LoggingClientHttpConnector` and `RequestLoggingExchangeFilterFunction`.

```java
WebClient webClient = WebClient.builder()
    .clientConnector(new LoggingClientHttpConnector())
    .filter(RequestLoggingExchangeFilterFunction.logRequest())
    .build();
```

### Examples in Action

![request-logging](https://raw.githubusercontent.com/dev-jonghoonpark/spring-http-logger/refs/heads/main/docs/request-logging.jpg)

![response-logging](https://raw.githubusercontent.com/dev-jonghoonpark/spring-http-logger/refs/heads/main/docs/response-logging.jpg)

### Logging config

`request/response bodies` are configured to be logged at the `INFO` level.
If you also want to see `request/response headers`, set the logging level to `DEBUG`.

```
logging.level.com.jonghoonpark=debug
```

## reference
- Portions of the code are adapted from:
  - rest client: https://github.com/spring-projects/spring-ai/issues/883#issuecomment-2739772323
  - web client (reactive): https://stackoverflow.com/a/73154616/29672082
