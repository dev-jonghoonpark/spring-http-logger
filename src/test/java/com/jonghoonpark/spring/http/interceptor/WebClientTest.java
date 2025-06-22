package com.jonghoonpark.spring.http.interceptor;

import java.util.List;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.jonghoonpark.spring.http.interceptor.reactive.LoggingClientHttpConnector;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.jonghoonpark.spring.http.interceptor.reactive.LoggingClientHttpResponse;
import com.jonghoonpark.spring.http.interceptor.reactive.RequestLoggingExchangeFilterFunction;
import com.jonghoonpark.spring.util.LogCaptureAppender;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebClientTest {

	@LocalServerPort
	int randomServerPort;

	@Test
	public void testLogging() {
		Logger responseLogger = (Logger) LoggerFactory.getLogger(LoggingClientHttpResponse.class);
		LogCaptureAppender responseLogCaptureAppender = new LogCaptureAppender();
		responseLogCaptureAppender.start();
		responseLogger.addAppender(responseLogCaptureAppender);

		Logger requestLogger = (Logger) LoggerFactory.getLogger(RequestLoggingExchangeFilterFunction.class);
		LogCaptureAppender requestLogCaptureAppender = new LogCaptureAppender();
		requestLogCaptureAppender.start();
		requestLogger.addAppender(requestLogCaptureAppender);

		HttpClient httpClient = HttpClient.create();
		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

		WebClient webClient = WebClient.builder()
			.clientConnector(new LoggingClientHttpConnector(connector))
			.filter(RequestLoggingExchangeFilterFunction.logRequest())
			.build();

		Mono<ResponseEntity<Void>> responseMono = webClient.post()
			.uri(String.format("http://localhost:%s/echo", randomServerPort))
			.bodyValue("TEST")
			.retrieve()
			.toBodilessEntity();

		StepVerifier.create(responseMono).assertNext(result -> {
			List<ILoggingEvent> requestLogEvents = requestLogCaptureAppender.getLogEvents();
			assertThat(requestLogEvents.size()).isEqualTo(3);
			assertThat(requestLogEvents.get(0).getFormattedMessage()).startsWith("Request:");
			assertThat(requestLogEvents.get(1).getFormattedMessage()).startsWith("Request headers:");
			assertThat(requestLogEvents.get(2).getFormattedMessage()).startsWith("Request body:");

			List<ILoggingEvent> responseLogEvents = responseLogCaptureAppender.getLogEvents();
			assertThat(responseLogEvents.size()).isEqualTo(3);
			assertThat(responseLogEvents.get(0).getFormattedMessage()).startsWith("Response status:");
			assertThat(responseLogEvents.get(1).getFormattedMessage()).startsWith("Response headers:");
			assertThat(responseLogEvents.get(2).getFormattedMessage()).startsWith("Response body:");
		}).verifyComplete();
	}

}
