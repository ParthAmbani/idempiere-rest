package com.idempiere.rest.serversync.service;

import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idempiere.rest.serversync.config.IdempiereConfig;
import com.idempiere.rest.serversync.dto.AuthRequest;
import com.idempiere.rest.serversync.dto.AuthResponse;
import com.idempiere.rest.serversync.dto.ErrorResponse;
import com.idempiere.rest.serversync.dto.RefreshTokenRequest;
import com.idempiere.rest.serversync.dto.RefreshTokenResponse;
import com.idempiere.rest.serversync.util.IdempiereEndpoints;

/**
 * OSGi-friendly IdempiereAuthService using Apache HttpClient.
 * Supports HTTPS with self-signed certificates.
 */
public class IdempiereAuthService {

    private final IdempiereConfig config;
    private final ObjectMapper mapper;

    public IdempiereAuthService(IdempiereConfig config) {
        this.config = config;
        this.mapper = new ObjectMapper();
    }

    public AuthResponse authenticate(AuthRequest request) throws Exception {
        String url = config.getEndpoint(IdempiereEndpoints.AUTH_TOKENS);
        return post(url, request, AuthResponse.class);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws Exception {
        String url = config.getEndpoint(IdempiereEndpoints.AUTH_REFRESH);
        return post(url, request, RefreshTokenResponse.class);
    }

    /**
     * Build a refresh-token request using the login request+response.
     */
    public RefreshTokenRequest buildRefreshRequest(AuthRequest loginRequest, AuthResponse loginResponse) {
        return new RefreshTokenRequest(
                loginResponse.getRefresh_token(),
                loginRequest.getParameters().getClientId(),
                loginResponse.getUserId()
        );
    }

    /**
     * Perform a POST request with JSON payload and map response to the given class.
     */
    private <T> T post(String url, Object payload, Class<T> clazz) throws Exception {
        String json = mapper.writeValueAsString(payload);

        // Build SSL context to trust self-signed certificates (for testing)
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(null, (certificate, authType) -> true)
                .build();

        try (CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build()) {

            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(post)) {
                int status = response.getStatusLine().getStatusCode();
                String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

                if (status >= 200 && status < 300) {
                    return mapper.readValue(body, clazz);
                } else {
                    ErrorResponse error = null;
                    try {
                        error = mapper.readValue(body, ErrorResponse.class);
                    } catch (Exception ex) {
                        throw new RuntimeException("Request failed with status " + status + " and body: " + body);
                    }
                    throw new RuntimeException(String.format(
                            "Request failed: %s (Status %d) - %s",
                            error.getTitle(), error.getStatus(), error.getDetail()
                    ));
                }
            }
        }
    }
}
