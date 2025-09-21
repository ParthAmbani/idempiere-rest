package com.idempiere.rest.serversync.service;

import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IdempiereRestClient {

	private final String token;
	private final CloseableHttpClient httpClient;
	private final ObjectMapper mapper = new ObjectMapper();

	public IdempiereRestClient(String token) throws Exception {
		this.token = token;

		// Build SSL context that trusts all certificates (self-signed support)
		SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build();

		this.httpClient = HttpClients.custom().setSSLContext(sslContext)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE) // Disable hostname verification
				.build();
	}

	public String post(String endpoint, String jsonPayload) throws Exception {
		HttpPost post = new HttpPost(endpoint);
		post.setHeader("Content-Type", "application/json");
		post.setHeader("Authorization", "Bearer " + token);
		post.setEntity(new StringEntity(jsonPayload, StandardCharsets.UTF_8));

		try (CloseableHttpResponse response = httpClient.execute(post)) {
			int status = response.getStatusLine().getStatusCode();
			String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

			if (status >= 200 && status < 300) {
				return body;
			} else {
				throw new RuntimeException("POST failed (" + status + "): " + body);
			}
		}
	}

	public <T> T postAndParse(String endpoint, String jsonPayload, Class<T> clazz) throws Exception {
		String responseBody = post(endpoint, jsonPayload);
		return mapper.readValue(responseBody, clazz);
	}

	public void close() throws Exception {
		httpClient.close();
	}
}
