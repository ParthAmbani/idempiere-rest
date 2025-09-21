package com.idempiere.rest.serversync.dto;

/**
 * Response for refresh endpoint.
 */
public class RefreshTokenResponse {
    private String token;
    private String refresh_token;

    public RefreshTokenResponse() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRefresh_token() { return refresh_token; }
    public void setRefresh_token(String refresh_token) { this.refresh_token = refresh_token; }
}
