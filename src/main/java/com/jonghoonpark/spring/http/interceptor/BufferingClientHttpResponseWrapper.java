package com.jonghoonpark.spring.http.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

public class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

	private final ClientHttpResponse response;

	private final byte[] body;

	public BufferingClientHttpResponseWrapper(ClientHttpResponse response, byte[] body) {
		this.response = response;
		this.body = body;
	}

	@Override
	public InputStream getBody() {
		return new ByteArrayInputStream(this.body);
	}

	@Override
	public HttpStatusCode getStatusCode() throws IOException {
		return this.response.getStatusCode();
	}

	@Override
	public HttpHeaders getHeaders() {
		return this.response.getHeaders();
	}

	@Override
	public void close() {
		this.response.close();
	}

	@Override
	public String getStatusText() throws IOException {
		return this.response.getStatusText();
	}

}
