package com.jonghoonpark.spring.http.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

	private final ClientHttpResponse response;

	private final byte[] body;

	public BufferingClientHttpResponseWrapper(ClientHttpResponse response, byte[] body) {
		this.response = response;
		this.body = body;
	}

	@Override
	public InputStream getBody() {
		return new ByteArrayInputStream(body);
	}

	@Override
	public HttpStatusCode getStatusCode() throws IOException {
		return response.getStatusCode();
	}

	@Override
	public HttpHeaders getHeaders() {
		return response.getHeaders();
	}

	@Override
	public void close() {
		response.close();
	}

	@Override
	public String getStatusText() throws IOException {
		return response.getStatusText();
	}

}