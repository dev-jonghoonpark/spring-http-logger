package com.jonghoonpark.spring.http.interceptor.reactive;

import java.net.URI;
import java.util.function.Function;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

public class LoggingClientHttpConnector implements ClientHttpConnector {

	private final ClientHttpConnector delegate;

	public LoggingClientHttpConnector() {
		this(new ReactorClientHttpConnector(HttpClient.create()));
	}

	public LoggingClientHttpConnector(ClientHttpConnector delegate) {
		this.delegate = delegate;
	}

	@Override
	public Mono<ClientHttpResponse> connect(HttpMethod method, URI uri,
			Function<? super ClientHttpRequest, Mono<Void>> requestCallback) {
		return this.delegate.connect(method, uri, requestCallback).map(LoggingClientHttpResponse::new);
	}

}
