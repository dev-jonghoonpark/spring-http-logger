# spring-http-logger

This library assists logging for Spring HTTP clients (RestClient, WebClient).

## How to use

### http client

Use `ClientLoggerRequestInterceptor`.

```java
RestClient restClient = RestClient.builder()
    .requestInterceptor(new ClientLoggerRequestInterceptor());
```

### web client (reactive)

Use `LoggingClientHttpConnector` and `RequestLoggingExchangeFilterFunction`.

```java
WebClient webClient = WebClient.builder()
    .clientConnector(new LoggingClientHttpConnector())
    .filter(RequestLoggingExchangeFilterFunction.logRequest())
    .build();
```

The `HttpClient` used in this code is part of `reactor-netty`. You might need to add it as a dependency.

## reference
- 
- Portions of the code are adapted from:
  - rest client: https://github.com/spring-projects/spring-ai/issues/883#issuecomment-2739772323
  - web client (reactive): https://stackoverflow.com/a/73154616/29672082
