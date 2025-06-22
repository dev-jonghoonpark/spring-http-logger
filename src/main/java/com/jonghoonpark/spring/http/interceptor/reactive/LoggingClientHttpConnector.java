package com.jonghoonpark.spring.http.interceptor.reactive;

import java.net.URI;
import java.util.function.Function;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;

public class LoggingClientHttpConnector implements ClientHttpConnector {

	private final ClientHttpConnector delegate;

	public LoggingClientHttpConnector(ClientHttpConnector delegate) {
		this.delegate = delegate;
	}

	@Override
	public Mono<ClientHttpResponse> connect(HttpMethod method, URI uri,
			Function<? super ClientHttpRequest, Mono<Void>> requestCallback) {
		return this.delegate.connect(method, uri, requestCallback).map(LoggingClientHttpResponse::new);
	}

}
