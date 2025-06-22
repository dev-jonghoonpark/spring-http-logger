# spring-http-logger

This library assists logging for Spring HTTP clients (RestClient, WebClient).

## Download

### gradle

```groovy
implementation 'com.jonghoonpark:spring-http-logger:1.0.0'
```

### maven

```xml
<dependency>
    <groupId>com.jonghoonpark</groupId>
    <artifactId>spring-http-logger</artifactId>
    <version>1.0.0</version>
</dependency>
```

## How to use

### http client

Use `ClientLoggerRequestInterceptor`.

```java
RestClient restClient = RestClient.builder()
    .requestInterceptor(new ClientLoggerRequestInterceptor())
    .build();
```

### web client (reactive)

Use `LoggingClientHttpConnector` and `RequestLoggingExchangeFilterFunction`.

```java
WebClient webClient = WebClient.builder()
    .clientConnector(new LoggingClientHttpConnector())
    .filter(RequestLoggingExchangeFilterFunction.logRequest())
    .build();
```

### logging config

Request/response bodies are configured to be logged at the INFO level.
If you also want to see request/response headers, set the logging level to DEBUG.

```
logging.level.com.jonghoonpark=debug
```

## reference
- 
- Portions of the code are adapted from:
  - rest client: https://github.com/spring-projects/spring-ai/issues/883#issuecomment-2739772323
  - web client (reactive): https://stackoverflow.com/a/73154616/29672082
