package com.idempiere.rest.serversync.dto;

/**
 * Refresh token request.
 */
public class RefreshTokenRequest {
    private String refresh_token;
    private int clientId;
    private int userId;

    public RefreshTokenRequest() {}

    public RefreshTokenRequest(String refresh_token, int clientId, int userId) {
        this.refresh_token = refresh_token;
        this.clientId = clientId;
        this.userId = userId;
    }

    public String getRefresh_token() { return refresh_token; }
    public void setRefresh_token(String refresh_token) { this.refresh_token = refresh_token; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
