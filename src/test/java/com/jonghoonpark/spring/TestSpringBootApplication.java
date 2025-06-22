package com.jonghoonpark.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TestSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringBootApplication.class, args);
	}

	@TestConfiguration
	public static class TestConfig {

		@RestController
		@RequestMapping("/echo")
		public static class EchoController {

			@PostMapping
			public ResponseEntity<String> echo(@RequestHeader HttpHeaders headers, @RequestBody String body) {
				return ResponseEntity.ok().headers(headers).body(body);
			}

		}

	}

}
