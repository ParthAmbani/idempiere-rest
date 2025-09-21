package com.idempiere.rest.serversync.dto;

/**
 * Successful authentication response.
 */
public class AuthResponse {
    private int userId;
    private String language;
    private int menuTreeId;
    private String token;
    private String refresh_token;

    public AuthResponse() {}

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public int getMenuTreeId() { return menuTreeId; }
    public void setMenuTreeId(int menuTreeId) { this.menuTreeId = menuTreeId; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRefresh_token() { return refresh_token; }
    public void setRefresh_token(String refresh_token) { this.refresh_token = refresh_token; }
}
