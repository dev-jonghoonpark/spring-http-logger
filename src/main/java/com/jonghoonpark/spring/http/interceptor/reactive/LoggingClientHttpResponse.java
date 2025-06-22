package com.jonghoonpark.spring.http.interceptor.reactive;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.client.reactive.ClientHttpResponseDecorator;

public class LoggingClientHttpResponse extends ClientHttpResponseDecorator {

	private static final Logger logger = LoggerFactory.getLogger(LoggingClientHttpResponse.class);

	public LoggingClientHttpResponse(ClientHttpResponse delegate) {
		super(delegate);
	}

	@Override
	public Flux<DataBuffer> getBody() {
		logger.info("Response status: {}", getDelegate().getStatusCode());
		logger.debug("Response headers: {}", getDelegate().getHeaders());

		return super.getBody().collectList().map(buffers -> {
			String responseBody = buffers.stream()
				.map(buffer -> buffer.toString(StandardCharsets.UTF_8))
				.collect(Collectors.joining());
			logger.info("Response body: {}", responseBody.replaceAll("\\s+", " "));
			return buffers;
		}).flatMapMany(Flux::fromIterable);
	}

}
