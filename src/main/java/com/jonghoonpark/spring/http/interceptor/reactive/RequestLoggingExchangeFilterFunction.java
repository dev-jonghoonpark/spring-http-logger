package com.jonghoonpark.spring.http.interceptor.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class RequestLoggingExchangeFilterFunction {

	private static final Logger logger = LoggerFactory.getLogger(RequestLoggingExchangeFilterFunction.class);

	public static ExchangeFilterFunction logRequest() {
		return (clientRequest, next) -> {
			logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
			logger.debug("Request headers: {}", clientRequest.headers());
			logger.info("Request body: {}", clientRequest.body());
			return next.exchange(clientRequest);
		};
	}

}
