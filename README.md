## How to use

### http client

```rest client
RestClient restClient = RestClient.builder()
    .requestInterceptor(new ClientLoggerRequestInterceptor());
```

### web client (reactive)

```java
HttpClient httpClient = HttpClient.create();
ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

WebClient webClient = WebClient.builder()
    .clientConnector(new LoggingClientHttpConnectorDecorator(connector))
    .filter(logRequest())
    .build();
```

The `HttpClient` used in this code is part of `reactor-netty`. You might need to add it as a dependency.

## reference

- rest client: https://github.com/spring-projects/spring-ai/issues/883#issuecomment-2739772323
- web client (reactive): https://stackoverflow.com/a/73154616/29672082