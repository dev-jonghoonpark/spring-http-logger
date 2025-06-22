package com.jonghoonpark.spring.http.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class ClientLoggerRequestInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ClientLoggerRequestInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		logRequest(request, body);
		var response = execution.execute(request, body);
		return logResponse(request, response);
	}

	private void logRequest(HttpRequest request, byte[] body) {
		logger.info("Request: {} {}", request.getMethod(), request.getURI());
		logger.debug("Request headers: {}", request.getHeaders());

		if (body != null && body.length > 0) {
			logger.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
		}
	}

	private ClientHttpResponse logResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
		logger.info("Response status: {}", response.getStatusCode());
		logger.debug("Response headers: {}", response.getHeaders());

		byte[] responseBody = response.getBody().readAllBytes();
		if (responseBody.length > 0) {
			logger.info("Response body: {}", new String(responseBody, StandardCharsets.UTF_8));
		}

		return new BufferingClientHttpResponseWrapper(response, responseBody);
	}

}
