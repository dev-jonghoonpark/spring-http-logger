package com.jonghoonpark.spring.http.interceptor;

import java.util.List;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import com.jonghoonpark.spring.util.LogCaptureAppender;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestClientTest {

	@LocalServerPort
	int randomServerPort;

	@Test
	public void testLogging() {
		Logger logger = (Logger) LoggerFactory.getLogger(ClientLoggerRequestInterceptor.class);
		LogCaptureAppender logCaptureAppender = new LogCaptureAppender();
		logCaptureAppender.start();
		logger.addAppender(logCaptureAppender);

		RestClient restClient = RestClient.builder().requestInterceptor(new ClientLoggerRequestInterceptor()).build();

		restClient.post()
			.uri(String.format("http://localhost:%s/echo", randomServerPort))
			.body("TEST")
			.retrieve()
			.toBodilessEntity();

		List<ILoggingEvent> logEvents = logCaptureAppender.getLogEvents();
		assertThat(logEvents.size()).isEqualTo(6);
		assertThat(logEvents.get(0).getFormattedMessage()).startsWith("Request:");
		assertThat(logEvents.get(1).getFormattedMessage()).startsWith("Request headers:");
		assertThat(logEvents.get(2).getFormattedMessage()).startsWith("Request body:");
		assertThat(logEvents.get(3).getFormattedMessage()).startsWith("Response status:");
		assertThat(logEvents.get(4).getFormattedMessage()).startsWith("Response headers:");
		assertThat(logEvents.get(5).getFormattedMessage()).startsWith("Response body:");
	}

}
